package com.project.app.base

interface IBaseFragment {
    fun createScreenStyles()
    fun showToolbar(type: Int = 1, color: Int? = null, title: String? = "", statusBarColor : Int? = null)
}