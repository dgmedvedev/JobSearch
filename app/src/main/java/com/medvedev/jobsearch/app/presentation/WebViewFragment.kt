package com.medvedev.jobsearch.app.presentation

import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.bundle.Bundle
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val binding by viewBinding(FragmentWebViewBinding::bind)

    private var link: String? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        link?.let { link ->
            openLink(link = link)
        }
    }

    private fun openLink(link: String) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = false
            settings.javaScriptCanOpenWindowsAutomatically = false
            try {
                loadUrl(link)
            } catch (e: Exception) {
                showToast(getString(R.string.error_loading_page, e.message.toString()))
            }
        }
    }

    private fun parseParams() {
        link = requireArguments().getString(ARG_LINK, DEFAULT_LINK)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.webView.destroy()
    }

    companion object {
        private const val ARG_LINK = "link"
        private const val DEFAULT_LINK = "https://time.is/"

        fun getInstance(link: String?): WebViewFragment =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LINK, link)
                }
            }
    }
}