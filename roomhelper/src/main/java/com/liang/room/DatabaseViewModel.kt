package com.liang.room

import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.RoomDatabase

abstract class DatabaseViewModel<Entity, DAO : BaseDao<Entity>>(application: Application) :
    AndroidViewModel(application) {

    private val dao: DAO
    private val dataObserve: LiveData<PagedList<Entity>>

    protected abstract fun getRoomDatabase(): RoomDatabase

    protected abstract fun getDao(): DAO

    /**
     * 每页加载多少数据，必须大于0，这里默认20
     *
     * @return 每页加载数据数量
     */
    protected open fun setPageSize(): Int {
        return 20
    }

    /**
     * 距底部还有几条数据时，加载下一页数据，默认为PageSize
     *
     * @return 距底部数据的数量
     */
    protected open fun setPrefetchDistance(): Int {
        return -1
    }

    /**
     * 第一次加载多少数据，必须是PageSize的倍数，默认为PageSize*3
     *
     * @return 第一次加载数据的数量 eg：PageSize*n
     */
    protected open fun setInitialLoadSizeHint(): Int {
        return -1
    }

    /**
     * 是否启用占位符，若为true，则视为固定数量的item
     *
     * @return 默认为true
     */
    protected open fun setEnablePlaceholders(): Boolean {
        return true
    }

    /**
     * 数据库没有查询到数据，可以通过触发网络等其他渠道加载数据到数据库
     */
    protected open fun onZeroItemsLoaded() {}

    /**
     * 可以忽略，因为我们只附加到数据库中的内容
     */
    protected open fun onItemAtFrontLoaded(itemAtFront: Entity) {}

    /**
     * 数据已到达列表末尾，可以通过触发网络加载更多数据到数据库
     */
    protected open fun onItemAtEndLoaded(itemAtEnd: Entity) {}


    open fun queryData(): DataSource.Factory<Int, Entity> {
        return dao.queryAll()
    }

    fun insert(data: Entity) {
        getRoomDatabase().runInTransaction {
            dao.insert(data)
        }
    }

    fun insert(data: List<Entity>) {
        getRoomDatabase().runInTransaction {
            dao.insert(data)
        }
    }

    fun update(data: Entity) {
        getRoomDatabase().runInTransaction {
            dao.update(data)
        }
    }

    fun update(data: List<Entity>) {
        getRoomDatabase().runInTransaction {
            dao.update(data)
        }
    }

    fun delete(data: Entity) {
        getRoomDatabase().runInTransaction {
            dao.delete(data)
        }
    }

    fun delete(data: List<Entity>) {
        getRoomDatabase().runInTransaction {
            dao.delete(data)
        }
    }

    private val dataBoundaryCallback: PagedList.BoundaryCallback<Entity> =
        object : PagedList.BoundaryCallback<Entity>() {

            @MainThread
            override fun onZeroItemsLoaded() {
                this@DatabaseViewModel.onZeroItemsLoaded()
            }

            @MainThread
            override fun onItemAtFrontLoaded(itemAtFront: Entity) {
                this@DatabaseViewModel.onItemAtFrontLoaded(itemAtFront)
            }

            @MainThread
            override fun onItemAtEndLoaded(itemAtEnd: Entity) {
                this@DatabaseViewModel.onItemAtEndLoaded(itemAtEnd)
            }
        }

    init {
        dao = getDao()
        val config = PagedList.Config.Builder()
            .setPageSize(setPageSize())
            .setPrefetchDistance(setPrefetchDistance())
            .setInitialLoadSizeHint(setInitialLoadSizeHint())
            .setEnablePlaceholders(setEnablePlaceholders())
        this.dataObserve = LivePagedListBuilder(
            queryData(), config.build()
        ).setBoundaryCallback(dataBoundaryCallback)
            .build()
    }

    fun databaseObserve(
        lifecycleOwner: LifecycleOwner,
        action: (PagedList<Entity>) -> Unit
    ) {
        dataObserve.observe(lifecycleOwner, Observer {
            action(it)
        })
    }

}