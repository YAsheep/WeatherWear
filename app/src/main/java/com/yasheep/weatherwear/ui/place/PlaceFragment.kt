package com.yasheep.weatherwear.ui.place

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yasheep.weatherwear.databinding.FragmentPlaceBinding

class PlaceFragment: Fragment() {

    val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }
    private lateinit var adapter: PlaceAdapter

    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding !!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_place, container, false)
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    //onActivityCreated() 方法被弃用的原因是
    // 它实际上与 Fragment 可用的 Activity 无关，也与完成其 onCreate() 的 Activity 无关。

    // navigation与宿主Activity的FragmentManger交互，
    // 这是添加到FragmentManger后最先被执行的生命周期函数
    // attaching fragments to their host activity，对应的还有onDetach()
    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().lifecycle.addObserver(object :LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event.targetState){
                    Lifecycle.State.CREATED ->{
                        val act = requireActivity() as AppCompatActivity
                        act.supportActionBar?.hide()
                    }
                    else -> {}
                }
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        binding.recyclerView.adapter = adapter
        // 发起搜索请求
        // 监听搜索框内容变化
        binding.searchPlaceEdit.addTextChangedListener {
                editable ->
            val contextEdit = editable.toString()
            if(contextEdit.isNotEmpty()){
                viewModel.searchPlaces(contextEdit)
            }else{
                // 搜索框为空，不显示列表
                binding.recyclerView.visibility = View.GONE
                binding.bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        //获取响应数据
        viewModel.placeLiveData.observe(this, Observer {
                result ->
            val places = result.getOrNull()
            if(places != null){
                binding.recyclerView.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "地点查询失败", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}