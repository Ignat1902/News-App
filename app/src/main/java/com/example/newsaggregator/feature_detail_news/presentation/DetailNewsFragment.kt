package com.example.newsaggregator.feature_detail_news.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsaggregator.MainActivity
import com.example.newsaggregator.R
import com.example.newsaggregator.databinding.FragmentDetailNewsBinding

class DetailNewsFragment : Fragment() {

    private val args: DetailNewsFragmentArgs by navArgs()

    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).binding.topAppBar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setOnClickListener {
                findNavController().navigateUp()
            }
        }

        fun setupWebView(url: String) {
            with(binding.webView) {
                settings.javaScriptEnabled = true

                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return false
                    }

                }

                loadUrl(url)
            }
        }

        setupWebView(args.url)
    }

    override fun onDestroyView() {
        binding.webView.stopLoading()
        binding.webView.destroy()
        super.onDestroyView()
        _binding = null
        (requireActivity() as MainActivity).binding.topAppBar.navigationIcon = null
    }


}