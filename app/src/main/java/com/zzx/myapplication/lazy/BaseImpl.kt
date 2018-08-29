package com.zzx.myapplication.lazy

/**
 * Created by donke on 2017/8/24.
 */
class BaseImpl(val x: Int) : Base {
    override fun get(): Int {
        return x
    }

    override fun print() {
        println(x)
    }
}