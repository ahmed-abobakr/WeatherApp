package com.example.weatherapp.home.mvvm.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DialogChooseCityBinding
import com.example.weatherapp.home.mvvm.factories.ChooseCityViewModelFactory
import com.example.weatherapp.home.mvvm.view_models.ChooseCityViewModel
import android.content.DialogInterface

class ChooseCityDialogFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogChooseCityBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_choose_city, container,
                                                false)

        val viewModel = ViewModelProviders.of(this, ChooseCityViewModelFactory(requireNotNull(activity)))
            .get(ChooseCityViewModel::class.java)

        binding.lifecycleOwner = this
        binding.chooseCityViewModel = viewModel
        binding.executePendingBindings()

        binding.dialogChooseCityBtnCancel.setOnClickListener { dismiss() }

        viewModel.cityLiveData.observe(this, Observer { city -> if(city.isNotEmpty())  this.dismiss() })

        return binding.root
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        val parentFragment = fragmentManager?.primaryNavigationFragment
        if (parentFragment is DialogInterface) {
            (parentFragment as DialogInterface).cancel()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onCancel(dialog)
        val parentFragment = fragmentManager?.primaryNavigationFragment
        if (parentFragment is DialogInterface) {
            (parentFragment as DialogInterface).dismiss()
        }
    }

}