package com.android.jijajuaap.ads


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback




import android.content.ContextWrapper
import androidx.activity.ComponentActivity


object AdsManager {

    private var mInterstitialAd: InterstitialAd? = null


    private const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    private const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"

    fun initialize(context: Context) {
        MobileAds.initialize(context)
        loadInterstitialAd(context)
    }

    private fun loadInterstitialAd(context: Context) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, INTERSTITIAL_AD_UNIT_ID, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    mInterstitialAd = ad
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }
            })
    }

    fun showInterstitialWhenLoaded(context: Context) {
        val activity = context.findActivity()
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {

            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(context, INTERSTITIAL_AD_UNIT_ID, adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        mInterstitialAd = ad
                        mInterstitialAd?.show(activity)
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        mInterstitialAd = null
                    }
                })
        }
    }


    @Composable
    fun Banner(modifier: Modifier = Modifier) {
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = BANNER_AD_UNIT_ID
                    loadAd(AdRequest.Builder().build())
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }


    private fun Context.findActivity(): ComponentActivity {
        var ctx = this
        while (ctx is ContextWrapper) {
            if (ctx is ComponentActivity) return ctx
            ctx = ctx.baseContext
        }
        throw IllegalStateException("No Activity found")
    }
}


