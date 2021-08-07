package com.project.app.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.project.app.R
import com.project.app.helpers.LoadingDialog
import com.project.app.helpers.ToolbarType
import kotlinx.coroutines.Job

abstract class BaseFragment<VB : ViewBinding> : Fragment(), IBaseFragment {
    lateinit var activityIndicator: LoadingDialog
    private var job = Job()
    private lateinit var topBarLayout: ConstraintLayout
    lateinit var leftButtonToolbar: ImageButton
    private lateinit var textViewMenuTitle: TextView
    private lateinit var rigthButtonToolbar: ImageButton

    private lateinit var fragmentView: View

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    @Suppress("UNCHECKED_CAST")
    val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topBarLayout = requireActivity().findViewById(R.id.topBarLayout)
        leftButtonToolbar = requireActivity().findViewById(R.id.leftButtonToolbar)
        rigthButtonToolbar = requireActivity().findViewById(R.id.rigthButtonToolbar)
        textViewMenuTitle = requireActivity().findViewById(R.id.menuItemTitle)
        fragmentView = view
        activityIndicator = LoadingDialog(requireActivity())
    }

    fun goToFragmentNoConnection(idAction: Int) {
        hideKeyboard()
        val graph = findNavController().navInflater.inflate(R.navigation.nav_graph)
        graph.startDestination = idAction
        findNavController().graph = graph
    }

    fun goToFragmentAction(idAction: Int, bundle: Bundle? = null) {
        hideKeyboard()
        if (bundle == null)
            findNavController().navigate(idAction)
        else
            findNavController().navigate(idAction, bundle)
    }

    @SuppressLint("ResourceType")
    override fun showToolbar(type: Int, color: Int?, title: String?, statusBarColor: Int?) {
        when (type) {
            ToolbarType.NONE.value -> {
                topBarLayout.visibility = View.GONE
                changeStatusBarColor(statusBarColor)
            }

            ToolbarType.BACKONLY.value -> {
                cleanFragment()
                leftButtonToolbar.setImageResource(R.drawable.arrow_left)
                leftButtonToolbar.visibility = View.VISIBLE
                textViewMenuTitle.visibility = View.VISIBLE
                textViewMenuTitle.text = title
                topBarLayout.visibility = View.VISIBLE
                leftButtonToolbar.setOnClickListener {
                    findNavController().navigateUp()
                }

//                changeStatusBarColor(R.color.black)
            }
        }
    }

    fun changeStatusBarColor(color: Int?) {
        if (color != null) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(requireContext(), color)
        }
    }

    private fun cleanFragment() {
        removeActions()
        hideToolbarElements()
    }

    private fun hideToolbarElements() {
        rigthButtonToolbar.visibility = View.INVISIBLE
        leftButtonToolbar.visibility = View.INVISIBLE
        textViewMenuTitle.visibility = View.INVISIBLE
    }

    private fun removeActions() {
        rigthButtonToolbar.setOnClickListener { }
        leftButtonToolbar.setOnClickListener { }
    }

    fun changeScreenTitle(idString: Int) {
        textViewMenuTitle.text = getString(idString)
    }

    override fun onResume() {
        super.onResume()
        createScreenStyles()
    }

    override fun onStart() {
        super.onStart()
        createScreenStyles()
    }

    fun showToast(mensaje: String) {
        Toast.makeText(activity, mensaje, Toast.LENGTH_SHORT).show()
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    fun setActionOpenMenuFrom(idAction: Int) {
        leftButtonToolbar.setOnClickListener {
            goToFragmentAction(idAction)
        }
    }

    fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}