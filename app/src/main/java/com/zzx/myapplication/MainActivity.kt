package com.zzx.myapplication

import android.app.Activity
import android.os.Bundle
import com.zzx.myapplication.lazy.Base
import com.zzx.myapplication.lazy.BaseImpl
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class MainActivity : Activity() {

    class Derived(b: Base) : Base by b

    /*private val ChangeCallback: (property: KProperty<*>, oldValue: String, newValue: String) -> Boolean = {
        property, oldValue, newValue ->
        true
    }*/


    val ChangeCallback: (property: KProperty<*>, oldValue: String, newValue: String) -> Unit = {
        property, oldValue, newValue ->
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val base    = BaseImpl(10)
        val derived = Derived(base)
        derived.print()

        val lazyValue: String by lazy {
            println(this)
            BaseImpl(10)
            "Hello"
        }
//        println(lazyValue)
//        println(lazyValue)

        var name: String by Delegates.observable("<initial>", onChange = ChangeCallback)/* {
            _, oldValue, newValue ->
            run {
                println("$oldValue -> $newValue")
                println("")
            }
        }*/
        name    = "first"
        name    = "second"
        println(name)

        var vetoable: Int by Delegates.vetoable(10) {
            property, oldValue, newValue ->
                println(oldValue)
                false
        }
        vetoable    = 0
        println(vetoable)
        val pair   = Pair("donkey", 29)
        val user = User(mapOf(
            "name" to "donkey",
            "age" to 29
        ))
        println(user.name)
        println(user.age)
        println(user.p)
        user.p = "tomy"
        println(user.p)

        var a: Long = 1
        val aInt: Int? = a as? Int
        println(aInt)
        val array = Array<String>(2) {
            "0"
        }
//        copy(array, null)
        set(Box())
    }

    fun copy(from: Array<out String>, to: Array<Any>?) {
        to!![0] = from[0]
    }

    fun fill(dest: Array<in String>, value:String) : String {
        var va = dest[0]
        return dest[0] as String
    }

    private fun set(dest: Box<in Int>) {
        dest.setData(10)
        dest.getData()
    }


    class User(map: Map<String, Any>) {
        val name: String by map
        val age: Int by map

        var p: String by Delegate<String>()
    }

    class Delegate<K> {
        private var value: String? = null
        operator fun <T>getValue(thisRef: T, property: KProperty<*>): String {
            println("$thisRef, thank you for give the '${property.name} ' to me")
            return value ?: ""
        }

        operator fun <T>setValue(thisRef: T, property: KProperty<*>, value: String) {
            println("$value has been set to '${property.name}' in $thisRef.")
            this.value  = value
        }

        fun nextValue(k: K) {

        }
    }

    class Box<T> {
        private var data: T? = null
        fun Box(data: T) {
            setData(data)
        }

        fun setData(data: T) {
            this.data = data
        }

        fun getData(): Int {
            return data as Int
        }
    }

}
