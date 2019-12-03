package com.liang.roomhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.liang.roomhelper.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private val adapter: UserObserveAdapter by lazy {
        UserObserveAdapter()
    }
    private val viewDataBinding: ActivityTestBinding by lazy {
        DataBindingUtil.setContentView<ActivityTestBinding>(this, R.layout.activity_test)
    }

    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewDataBinding.recyclerView.adapter = adapter
        viewModel.databaseObserve(this) { adapter.submitList(it) }
        databaseSet()
    }

    private fun databaseSet() {
        var age = 0
        viewDataBinding.button.setOnClickListener{
            val id = viewDataBinding.editText.text.toString()
            if (id.isNotEmpty()) {
                val user = UserEntity(Integer.parseInt(id), "add/update = $id", age)
                age++
                viewModel.insert(user)
            }
        }

        viewDataBinding.button1.setOnClickListener {
            val id = viewDataBinding.editText1.text.toString()
            if (id.isNotEmpty()) {
                val user = UserEntity(Integer.parseInt(id), "", 0)
                viewModel.delete(user)

            }
        }
    }
}
