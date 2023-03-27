package com.example.littlelemon

import android.app.Application
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.*
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAmount
import java.util.concurrent.Flow

@Entity
data class MenuItem(
    @PrimaryKey val id : Int,
    val title: String,
    val desc: String,
    val price : String,
    val image : String,
    val category: String
)


@Dao
interface MenuDao{
    @Query("SELECT * FROM MenuItem")
    fun getAll() : LiveData<List<MenuItem>>

    @Insert
    fun add(vararg menuItem: MenuItem)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItem) == 0")
    fun isEmpty(): Boolean

    @Query ( "SELECT * FROM MenuItem WHERE category = :category")
    fun filter(category:String) : MenuItem
}

@Database(entities = [MenuItem::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun menuItemDao(): MenuDao
}





@Entity
data class Orderd(
    @PrimaryKey(autoGenerate = true) var id: Long =0L,
    val status : Status = Status.NO,
    val customerEmail: String?,
    val customerPhone : String = "",
    val totalAmount: Double = 0.00,
    var orderBody: MutableMap<String,Int> ,
    var paymentMethod: Payment = Payment.THINKING,
    val checkReady: Ready = Ready.NOT_READY,
)

enum class Ready{
    READY,
    NOT_READY
}
enum class Payment{
    CARD,
    CASH,
    THINKING
}
enum class Status{
    NO,
    APPLIED,
    READY_TO_DELIVER,
    DELIVERING,
    DELIVERED
}



@Dao
interface OrderDao{
    @Query("SELECT * FROM Orderd")
    fun getAll() : LiveData<List<Orderd>>

    @Upsert
    suspend fun add(vararg order: Orderd)

    @Query("SELECT (SELECT COUNT(*) FROM Orderd) == 0")
    suspend fun isEmpty(): Boolean

    @Query ( "SELECT * FROM Orderd where id = :id")
    suspend fun filter(id: Long) : Orderd

    @Delete ()
    fun del(orderItem: Orderd)
}

@Database(entities = [Orderd::class], version = 1)
@TypeConverters(MapConverter::class)
abstract class OrderDatabase: RoomDatabase(){
    abstract fun orderItemDao(): OrderDao
    companion object{

            private var INSTANCE : OrderDatabase? = null
        fun getInstance(context: Context): OrderDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance= Room.databaseBuilder(
                        context.applicationContext
                        ,OrderDatabase::class.java,
                        "orderdata")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE= instance
                }
                return instance
            }

        }
    }
}


class OrderRepository(private val orderdao : OrderDao){
    val readAllData : LiveData<List<Orderd>> = orderdao.getAll()

    suspend fun add(orderItem: Orderd){

        orderdao.add(orderItem)
    }
    suspend fun emptyCheck(): Boolean{

        return orderdao.isEmpty()

    }

    suspend fun delete(orderItem: Orderd){

        orderdao.del(orderItem)

}}

class OrderViewModel(application : Application): AndroidViewModel(application){
    val allOders : LiveData<List<Orderd>>
    private val repo: OrderRepository

    init {
        val orderDb = OrderDatabase.getInstance(application)
        val orderDao = orderDb.orderItemDao()
        repo = OrderRepository(orderDao)

        allOders = repo.readAllData
    }
        fun add(order : Orderd){
            viewModelScope.launch(Dispatchers.IO) {
                repo.add(order)
            }
        }

        fun isEmpty() : Boolean{

            var check : Boolean = false

            viewModelScope.launch(Dispatchers.IO) {
                check = repo.emptyCheck()
            }

            return check
        }

        fun remove(orderItem: Orderd){

            viewModelScope.launch(Dispatchers.IO) {
                repo.delete(orderItem)
            }
        }
    }

class MainViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderViewModel(application) as T
    }
}


class MapConverter{
    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
    @TypeConverter
    fun MapToJson(value : MutableMap<String,Int>): String{
        return  Gson().toJson(value)
    }

    @TypeConverter
    fun JsonToMap(value : String): MutableMap<String,Int>{
        return  try {
            Gson().fromJson<MutableMap<String, Int>>(value)
        }
        catch (e : Exception){
            mutableMapOf()
        }
    }

}