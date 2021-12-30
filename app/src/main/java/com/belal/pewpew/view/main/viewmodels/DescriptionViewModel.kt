package com.belal.pewpew.view.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belal.pewpew.model.menumodel.MenuModelItem


class DescriptionViewModel : ViewModel() {

    var selectedItemId = MutableLiveData<MenuModelItem>()


}