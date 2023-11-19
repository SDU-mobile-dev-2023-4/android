package dk.sdu.weshare.api

import java.time.Duration

class CacheProxy<T : Any> {
    private var data: T? = null
    private var lastUpdated: Duration? = null;



    fun get(): T? {
        print("Cache: hit with" + data.toString())
        return data
    }

    fun set(data: T): CacheProxy<T> {
        this.data = data
        this.lastUpdated = Duration.ZERO
        print("Cache: set with" + data.toString())
        return this
    }

    fun isStale(): Boolean {
        return (lastUpdated?.toMinutes() ?: 0) > 5
    }


}