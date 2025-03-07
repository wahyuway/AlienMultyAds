package com.aliendroid.alienads;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.applovin.sdk.AppLovinSdk;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedAdListener;
import com.mopub.mobileads.MoPubRewardedAds;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import java.util.Map;
import java.util.Set;

public class AliendroidReward {
    public static MaxRewardedAd rewardedAd;
    public static boolean unlockreward = false;
    public static MoPubRewardedAdListener rewardedAdListener;
    public static AppLovinIncentivizedInterstitial incentivizedInterstitial;
    public static StartAppAd rewardedVideo;
    private static RewardedAd mRewardedAd;

    public static void LoadRewardAdmob(Activity activity, String selectBackupAds, String idReward, String idBackupReward) {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(activity, idReward,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;

                    }
                });
        switch (selectBackupAds) {
            case "APPLOVIN-M":
                rewardedAd = MaxRewardedAd.getInstance(idBackupReward, activity);
                rewardedAd.loadAd();
                MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {

                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        unlockreward = true;
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                };
                rewardedAd.setListener(maxRewardedAdListener);
                break;
            case "MOPUB":
                MoPubRewardedAds.loadRewardedAd(idBackupReward);
                break;
            case "APPLOVIN-D":
                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(idBackupReward, AppLovinSdk.getInstance(activity));
                incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd) {
                        // A rewarded video was successfully received.
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // A rewarded video failed to load.
                    }
                });
                break;
            case "IRON":
                IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                    @Override
                    public void onRewardedVideoAdOpened() {
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                    }

                    @Override
                    public void onRewardedVideoAvailabilityChanged(boolean available) {
                    }

                    @Override
                    public void onRewardedVideoAdRewarded(Placement placement) {
                        unlockreward = true;
                    }

                    @Override
                    public void onRewardedVideoAdShowFailed(IronSourceError error) {
                    }

                    @Override
                    public void onRewardedVideoAdClicked(Placement placement) {
                    }

                    @Override
                    public void onRewardedVideoAdStarted() {
                    }

                    @Override
                    public void onRewardedVideoAdEnded() {
                    }
                });
                break;
            case "STARTAPP":
                rewardedVideo = new StartAppAd(activity);
                rewardedVideo.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {
                        unlockreward = true;
                    }
                });

                rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }
                });
                break;
            case "UNITY":
                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                    unlockreward=true;
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

                    }
                });
                break;

        }
    }

    public static void LoadRewardUnity(Activity activity, String selectBackupAds, String idReward, String idBackupReward) {
        UnityAds.addListener(new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String placementId) {

            }

            @Override
            public void onUnityAdsStart(String placementId) {

            }

            @Override
            public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                unlockreward = true;
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
                switch (selectBackupAds) {
                    case "GOOGLE-ADS":
                    case "ADMOB":
                        if (mRewardedAd != null) {
                            Activity activityContext = activity;
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    unlockreward = true;
                                }
                            });
                        }
                        break;
                    case "MOPUB":
                        MoPubRewardedAds.showRewardedAd(idBackupReward);
                        rewardedAdListener = new MoPubRewardedAdListener() {
                            @Override
                            public void onRewardedAdLoadSuccess(String adUnitId) {
                                // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
                            }

                            @Override
                            public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                            }

                            @Override
                            public void onRewardedAdStarted(String adUnitId) {
                                // Called when a rewarded ad starts playing.
                            }

                            @Override
                            public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                                //  Called when there is an error while attempting to show the ad.
                            }

                            @Override
                            public void onRewardedAdClicked(@NonNull String adUnitId) {
                                //  Called when a rewarded ad is clicked.
                            }

                            @Override
                            public void onRewardedAdClosed(String adUnitId) {
                                // Called when a rewarded ad is closed. At this point your application should resume.
                            }

                            @Override
                            public void onRewardedAdCompleted(Set<String> adUnitIds, MoPubReward reward) {
                                unlockreward = true;

                            }
                        };
                        MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
                        break;
                    case "APPLOVIN-M":
                        if (rewardedAd.isReady()) {
                            rewardedAd.showAd();
                        }
                        break;
                    case "APPLOVIN-D":
                        if (incentivizedInterstitial != null) {
                            // A rewarded video is available.  Call the show method with the listeners you want to use.
                            // We will use the display listener to preload the next rewarded video when this one finishes.
                            incentivizedInterstitial.show(activity, new AppLovinAdRewardListener() {
                                @Override
                                public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
                                    unlockreward = true;
                                }

                                @Override
                                public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

                                }

                                @Override
                                public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

                                }

                                @Override
                                public void validationRequestFailed(AppLovinAd ad, int errorCode) {

                                }

                                @Override
                                public void userDeclinedToViewAd(AppLovinAd ad) {

                                }
                            }, null, new AppLovinAdDisplayListener() {
                                @Override
                                public void adDisplayed(AppLovinAd appLovinAd) {
                                    // A rewarded video is being displayed.
                                }

                                @Override
                                public void adHidden(AppLovinAd appLovinAd) {
                                    // A rewarded video was closed.  Preload the next video now.  We won't use a load listener.
                                    incentivizedInterstitial.preload(null);
                                }
                            });
                        }
                        break;
                    case "STARTAPP":
                        rewardedVideo.showAd();
                        break;
                    case "IRON":
                        IronSource.showRewardedVideo(idBackupReward);
                        break;
                }
            }
        });

        switch (selectBackupAds) {
            case "ADMOB":
            case "GOOGLE-ADS":
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(activity, idBackupReward,
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mRewardedAd = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd = rewardedAd;

                            }
                        });
                break;
            case "APPLOVIN-M":
                rewardedAd = MaxRewardedAd.getInstance(idBackupReward, activity);
                rewardedAd.loadAd();
                MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {

                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        unlockreward = true;
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                };
                rewardedAd.setListener(maxRewardedAdListener);
                break;
            case "MOPUB":
                MoPubRewardedAds.loadRewardedAd(idBackupReward);
                break;
            case "APPLOVIN-D":
                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(idBackupReward, AppLovinSdk.getInstance(activity));
                incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd) {
                        // A rewarded video was successfully received.
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // A rewarded video failed to load.
                    }
                });
                break;
            case "IRON":
                IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                    @Override
                    public void onRewardedVideoAdOpened() {
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                    }

                    @Override
                    public void onRewardedVideoAvailabilityChanged(boolean available) {
                    }

                    @Override
                    public void onRewardedVideoAdRewarded(Placement placement) {
                        unlockreward = true;
                    }

                    @Override
                    public void onRewardedVideoAdShowFailed(IronSourceError error) {
                    }

                    @Override
                    public void onRewardedVideoAdClicked(Placement placement) {
                    }

                    @Override
                    public void onRewardedVideoAdStarted() {
                    }

                    @Override
                    public void onRewardedVideoAdEnded() {
                    }
                });
                break;
            case "STARTAPP":
                rewardedVideo = new StartAppAd(activity);
                rewardedVideo.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {
                        unlockreward = true;
                    }
                });

                rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }
                });
                break;

        }
    }

    public static void LoadRewardGoogleAds(Activity activity, String selectBackupAds, String idReward, String idBackupReward) {
        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
        RewardedAd.load(activity, idReward,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;

                    }
                });
        switch (selectBackupAds) {
            case "APPLOVIN-M":
                rewardedAd = MaxRewardedAd.getInstance(idBackupReward, activity);
                rewardedAd.loadAd();
                MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {

                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        unlockreward = true;
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                };
                rewardedAd.setListener(maxRewardedAdListener);
                break;
            case "MOPUB":
                MoPubRewardedAds.loadRewardedAd(idBackupReward);
                break;
            case "APPLOVIN-D":
                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(idBackupReward, AppLovinSdk.getInstance(activity));
                incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd) {
                        // A rewarded video was successfully received.
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // A rewarded video failed to load.
                    }
                });
                break;
            case "IRON":
                IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                    @Override
                    public void onRewardedVideoAdOpened() {
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                    }

                    @Override
                    public void onRewardedVideoAvailabilityChanged(boolean available) {
                    }

                    @Override
                    public void onRewardedVideoAdRewarded(Placement placement) {
                        unlockreward = true;
                    }

                    @Override
                    public void onRewardedVideoAdShowFailed(IronSourceError error) {
                    }

                    @Override
                    public void onRewardedVideoAdClicked(Placement placement) {
                    }

                    @Override
                    public void onRewardedVideoAdStarted() {
                    }

                    @Override
                    public void onRewardedVideoAdEnded() {
                    }
                });
                break;
            case "STARTAPP":
                rewardedVideo = new StartAppAd(activity);
                rewardedVideo.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {
                        unlockreward = true;
                    }
                });

                rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }
                });
                break;
            case "UNITY":
                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        unlockreward=true;
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

                    }
                });
                break;

        }
    }


    public static void LoadRewardApplovinMax(Activity activity, String selectBackupAds, String idReward, String idBackupReward) {
        rewardedAd = MaxRewardedAd.getInstance(idReward, activity);
        rewardedAd.loadAd();
        MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
            @Override
            public void onRewardedVideoStarted(MaxAd ad) {

            }

            @Override
            public void onRewardedVideoCompleted(MaxAd ad) {
                unlockreward = true;
            }

            @Override
            public void onUserRewarded(MaxAd ad, MaxReward reward) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        };
        rewardedAd.setListener(maxRewardedAdListener);
        switch (selectBackupAds) {
            case "ADMOB":
            case "GOOGLE-ADS":
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(activity, idBackupReward,
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mRewardedAd = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd = rewardedAd;

                            }
                        });
                break;
            case "MOPUB":
                MoPubRewardedAds.loadRewardedAd(idBackupReward);
                break;
            case "APPLOVIN-D":
                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(idBackupReward, AppLovinSdk.getInstance(activity));
                incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd) {
                        // A rewarded video was successfully received.
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // A rewarded video failed to load.
                    }
                });
                break;
            case "IRON":
                IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                    @Override
                    public void onRewardedVideoAdOpened() {
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                    }

                    @Override
                    public void onRewardedVideoAvailabilityChanged(boolean available) {
                    }

                    @Override
                    public void onRewardedVideoAdRewarded(Placement placement) {
                        unlockreward = true;
                    }

                    @Override
                    public void onRewardedVideoAdShowFailed(IronSourceError error) {
                    }

                    @Override
                    public void onRewardedVideoAdClicked(Placement placement) {
                    }

                    @Override
                    public void onRewardedVideoAdStarted() {
                    }

                    @Override
                    public void onRewardedVideoAdEnded() {
                    }
                });
                break;
            case "STARTAPP":
                rewardedVideo = new StartAppAd(activity);
                rewardedVideo.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {
                        unlockreward = true;
                    }
                });

                rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }
                });
                break;
            case "UNITY":
                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        unlockreward=true;
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

                    }
                });
                break;

        }
    }

    public static void LoadRewardApplovinDis(Activity activity, String selectBackupAds, String idReward, String idBackupReward) {
        incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(idReward, AppLovinSdk.getInstance(activity));
        incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd) {
                // A rewarded video was successfully received.
            }

            @Override
            public void failedToReceiveAd(int errorCode) {
                // A rewarded video failed to load.
            }
        });
        switch (selectBackupAds) {
            case "ADMOB":
            case "GOOGLE-ADS":
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(activity, idBackupReward,
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mRewardedAd = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd = rewardedAd;

                            }
                        });
                break;
            case "MOPUB":
                MoPubRewardedAds.loadRewardedAd(idBackupReward);
                break;
            case "APPLOVIN-M":
                rewardedAd = MaxRewardedAd.getInstance(idBackupReward, activity);
                rewardedAd.loadAd();
                MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {

                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        unlockreward = true;
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                };
                rewardedAd.setListener(maxRewardedAdListener);

                break;
            case "IRON":
                IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                    @Override
                    public void onRewardedVideoAdOpened() {
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                    }

                    @Override
                    public void onRewardedVideoAvailabilityChanged(boolean available) {
                    }

                    @Override
                    public void onRewardedVideoAdRewarded(Placement placement) {
                        unlockreward = true;
                    }

                    @Override
                    public void onRewardedVideoAdShowFailed(IronSourceError error) {
                    }

                    @Override
                    public void onRewardedVideoAdClicked(Placement placement) {
                    }

                    @Override
                    public void onRewardedVideoAdStarted() {
                    }

                    @Override
                    public void onRewardedVideoAdEnded() {
                    }
                });
                break;
            case "STARTAPP":
                rewardedVideo = new StartAppAd(activity);
                rewardedVideo.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {
                        unlockreward = true;
                    }
                });

                rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }
                });
                break;
            case "UNITY":
                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        unlockreward=true;
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

                    }
                });
                break;

        }
    }

    public static void LoadRewardMopub(Activity activity, String selectBackupAds, String idReward, String idBackupReward) {
        MoPubRewardedAds.loadRewardedAd(idReward);
        switch (selectBackupAds) {
            case "ADMOB":
            case "GOOGLE-ADS":
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(activity, idBackupReward,
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mRewardedAd = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd = rewardedAd;

                            }
                        });
                break;
            case "APPLOVIN-D":
                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(idBackupReward, AppLovinSdk.getInstance(activity));
                incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd) {
                        // A rewarded video was successfully received.
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // A rewarded video failed to load.
                    }
                });
                break;
            case "APPLOVIN-M":
                rewardedAd = MaxRewardedAd.getInstance(idBackupReward, activity);
                rewardedAd.loadAd();
                MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {

                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        unlockreward = true;
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                };
                rewardedAd.setListener(maxRewardedAdListener);

                break;
            case "IRON":
                IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                    @Override
                    public void onRewardedVideoAdOpened() {
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                    }

                    @Override
                    public void onRewardedVideoAvailabilityChanged(boolean available) {
                    }

                    @Override
                    public void onRewardedVideoAdRewarded(Placement placement) {
                        unlockreward = true;
                    }

                    @Override
                    public void onRewardedVideoAdShowFailed(IronSourceError error) {
                    }

                    @Override
                    public void onRewardedVideoAdClicked(Placement placement) {
                    }

                    @Override
                    public void onRewardedVideoAdStarted() {
                    }

                    @Override
                    public void onRewardedVideoAdEnded() {
                    }
                });
                break;
            case "STARTAPP":
                rewardedVideo = new StartAppAd(activity);
                rewardedVideo.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {
                        unlockreward = true;
                    }
                });

                rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }
                });
                break;
            case "UNITY":
                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        unlockreward=true;
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

                    }
                });
                break;
        }
    }

    public static void LoadRewardIron(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {
        IronSource.setRewardedVideoListener(new RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
            }

            @Override
            public void onRewardedVideoAvailabilityChanged(boolean available) {
            }

            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                unlockreward = true;
            }

            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError error) {
                switch (selecBackuptAds) {
                    case "GOOGLE-ADS":
                    case "ADMOB":
                        if (mRewardedAd != null) {
                            Activity activityContext = activity;
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    unlockreward = true;
                                }
                            });
                        }
                        break;
                    case "MOPUB":
                        MoPubRewardedAds.showRewardedAd(idBackupReward);
                        rewardedAdListener = new MoPubRewardedAdListener() {
                            @Override
                            public void onRewardedAdLoadSuccess(String adUnitId) {
                                // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
                            }

                            @Override
                            public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                            }

                            @Override
                            public void onRewardedAdStarted(String adUnitId) {
                                // Called when a rewarded ad starts playing.
                            }

                            @Override
                            public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                                //  Called when there is an error while attempting to show the ad.
                            }

                            @Override
                            public void onRewardedAdClicked(@NonNull String adUnitId) {
                                //  Called when a rewarded ad is clicked.
                            }

                            @Override
                            public void onRewardedAdClosed(String adUnitId) {
                                // Called when a rewarded ad is closed. At this point your application should resume.
                            }

                            @Override
                            public void onRewardedAdCompleted(Set<String> adUnitIds, MoPubReward reward) {
                                unlockreward = true;

                            }
                        };
                        MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
                        break;
                    case "APPLOVIN-M":
                        if (rewardedAd.isReady()) {
                            rewardedAd.showAd();
                        }
                        break;
                    case "APPLOVIN-D":
                        if (incentivizedInterstitial != null) {
                            // A rewarded video is available.  Call the show method with the listeners you want to use.
                            // We will use the display listener to preload the next rewarded video when this one finishes.
                            incentivizedInterstitial.show(activity, new AppLovinAdRewardListener() {
                                @Override
                                public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
                                    unlockreward = true;
                                }

                                @Override
                                public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

                                }

                                @Override
                                public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

                                }

                                @Override
                                public void validationRequestFailed(AppLovinAd ad, int errorCode) {

                                }

                                @Override
                                public void userDeclinedToViewAd(AppLovinAd ad) {

                                }
                            }, null, new AppLovinAdDisplayListener() {
                                @Override
                                public void adDisplayed(AppLovinAd appLovinAd) {
                                    // A rewarded video is being displayed.
                                }

                                @Override
                                public void adHidden(AppLovinAd appLovinAd) {
                                    // A rewarded video was closed.  Preload the next video now.  We won't use a load listener.
                                    incentivizedInterstitial.preload(null);
                                }
                            });
                        }
                        break;
                    case "STARTAPP":
                        rewardedVideo.showAd();
                        break;
                    case "UNITY":
                        if (UnityAds.isReady (idBackupReward)) {
                            UnityAds.show (activity, idBackupReward);
                        }
                        break;
                }
            }

            @Override
            public void onRewardedVideoAdClicked(Placement placement) {
            }

            @Override
            public void onRewardedVideoAdStarted() {
            }

            @Override
            public void onRewardedVideoAdEnded() {
            }
        });
        switch (selecBackuptAds) {
            case "ADMOB":
            case "GOOGLE-ADS":
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(activity, idBackupReward,
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mRewardedAd = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd = rewardedAd;

                            }
                        });
                break;
            case "APPLOVIN-D":
                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(idBackupReward, AppLovinSdk.getInstance(activity));
                incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd) {
                        // A rewarded video was successfully received.
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // A rewarded video failed to load.
                    }
                });
                break;
            case "APPLOVIN-M":
                rewardedAd = MaxRewardedAd.getInstance(idBackupReward, activity);
                rewardedAd.loadAd();
                MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {

                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        unlockreward = true;
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                };
                rewardedAd.setListener(maxRewardedAdListener);

                break;
            case "MOPUB":
                MoPubRewardedAds.loadRewardedAd(idBackupReward);
                break;
            case "STARTAPP":
                rewardedVideo = new StartAppAd(activity);
                rewardedVideo.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {
                        unlockreward = true;
                    }
                });

                rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

                    }
                });
                break;
            case "UNITY":
                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        unlockreward=true;
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

                    }
                });
                break;

        }
    }

    public static void LoadRewardStartApp(Activity activity, String selectBackupAds, String idReward, String idBackupReward) {
        rewardedVideo = new StartAppAd(activity);
        rewardedVideo.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {
                unlockreward = true;
            }
        });

        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

            }

            @Override
            public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

            }
        });
        switch (selectBackupAds) {
            case "ADMOB":
            case "GOOGLE-ADS":
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(activity, idBackupReward,
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mRewardedAd = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd = rewardedAd;

                            }
                        });
                break;
            case "APPLOVIN-D":
                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(idBackupReward, AppLovinSdk.getInstance(activity));
                incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd) {
                        // A rewarded video was successfully received.
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // A rewarded video failed to load.
                    }
                });
                break;
            case "APPLOVIN-M":
                rewardedAd = MaxRewardedAd.getInstance(idBackupReward, activity);
                rewardedAd.loadAd();
                MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {

                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        unlockreward = true;
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                };
                rewardedAd.setListener(maxRewardedAdListener);

                break;
            case "MOPUB":
                MoPubRewardedAds.loadRewardedAd(idBackupReward);
                break;
            case "IRON":
                IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                    @Override
                    public void onRewardedVideoAdOpened() {
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                    }

                    @Override
                    public void onRewardedVideoAvailabilityChanged(boolean available) {
                    }

                    @Override
                    public void onRewardedVideoAdRewarded(Placement placement) {
                        unlockreward = true;
                    }

                    @Override
                    public void onRewardedVideoAdShowFailed(IronSourceError error) {
                    }

                    @Override
                    public void onRewardedVideoAdClicked(Placement placement) {
                    }

                    @Override
                    public void onRewardedVideoAdStarted() {
                    }

                    @Override
                    public void onRewardedVideoAdEnded() {
                    }
                });
                break;

            case "UNITY":
                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        unlockreward=true;
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

                    }
                });
                break;

        }
    }


    public static void ShowRewardAdmob(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {
        if (mRewardedAd != null) {
            Activity activityContext = activity;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    unlockreward = true;
                    LoadRewardAdmob(activity, selecBackuptAds, idReward, idBackupReward);
                }
            });
        } else {
            LoadRewardAdmob(activity, selecBackuptAds, idReward, idBackupReward);
            switch (selecBackuptAds) {
                case "APPLOVIN-M":
                    if (rewardedAd.isReady()) {
                        rewardedAd.showAd();
                    }
                    break;
                case "MOPUB":
                    MoPubRewardedAds.showRewardedAd(idBackupReward);
                    rewardedAdListener = new MoPubRewardedAdListener() {
                        @Override
                        public void onRewardedAdLoadSuccess(String adUnitId) {
                            // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
                        }

                        @Override
                        public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                        }

                        @Override
                        public void onRewardedAdStarted(String adUnitId) {
                            // Called when a rewarded ad starts playing.
                        }

                        @Override
                        public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                            //  Called when there is an error while attempting to show the ad.
                        }

                        @Override
                        public void onRewardedAdClicked(@NonNull String adUnitId) {
                            //  Called when a rewarded ad is clicked.
                        }

                        @Override
                        public void onRewardedAdClosed(String adUnitId) {
                            // Called when a rewarded ad is closed. At this point your application should resume.
                        }

                        @Override
                        public void onRewardedAdCompleted(Set<String> adUnitIds, MoPubReward reward) {
                            unlockreward = true;

                        }
                    };
                    MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
                    break;
                case "APPLOVIN-D":
                    if (incentivizedInterstitial != null) {
                        // A rewarded video is available.  Call the show method with the listeners you want to use.
                        // We will use the display listener to preload the next rewarded video when this one finishes.
                        incentivizedInterstitial.show(activity, new AppLovinAdRewardListener() {
                            @Override
                            public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
                                unlockreward = true;
                            }

                            @Override
                            public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

                            }

                            @Override
                            public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

                            }

                            @Override
                            public void validationRequestFailed(AppLovinAd ad, int errorCode) {

                            }

                            @Override
                            public void userDeclinedToViewAd(AppLovinAd ad) {

                            }
                        }, null, new AppLovinAdDisplayListener() {
                            @Override
                            public void adDisplayed(AppLovinAd appLovinAd) {
                                // A rewarded video is being displayed.
                            }

                            @Override
                            public void adHidden(AppLovinAd appLovinAd) {
                                // A rewarded video was closed.  Preload the next video now.  We won't use a load listener.
                                incentivizedInterstitial.preload(null);
                            }
                        });
                    }
                    break;
                case "IRON":
                    IronSource.showRewardedVideo(idBackupReward);
                    break;
                case "STARTAPP":

                    if (rewardedVideo.isReady()) {
                        rewardedVideo.showAd();
                    }
                    break;
                case "UNITY":
                    if (UnityAds.isReady (idBackupReward)) {
                        UnityAds.show (activity, idBackupReward);
                    }
                    break;
            }
        }
    }

    public static void ShowRewardGoogleAds(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {
        if (mRewardedAd != null) {
            Activity activityContext = activity;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    unlockreward = true;
                    LoadRewardGoogleAds(activity, selecBackuptAds, idReward, idBackupReward);
                }
            });
        } else {
            LoadRewardGoogleAds(activity, selecBackuptAds, idReward, idBackupReward);
            switch (selecBackuptAds) {
                case "APPLOVIN-M":
                    if (rewardedAd.isReady()) {
                        rewardedAd.showAd();
                    }
                    break;
                case "MOPUB":
                    MoPubRewardedAds.showRewardedAd(idBackupReward);
                    rewardedAdListener = new MoPubRewardedAdListener() {
                        @Override
                        public void onRewardedAdLoadSuccess(String adUnitId) {
                            // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
                        }

                        @Override
                        public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                        }

                        @Override
                        public void onRewardedAdStarted(String adUnitId) {
                            // Called when a rewarded ad starts playing.
                        }

                        @Override
                        public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                            //  Called when there is an error while attempting to show the ad.
                        }

                        @Override
                        public void onRewardedAdClicked(@NonNull String adUnitId) {
                            //  Called when a rewarded ad is clicked.
                        }

                        @Override
                        public void onRewardedAdClosed(String adUnitId) {
                            // Called when a rewarded ad is closed. At this point your application should resume.
                        }

                        @Override
                        public void onRewardedAdCompleted(Set<String> adUnitIds, MoPubReward reward) {
                            unlockreward = true;

                        }
                    };
                    MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
                    break;
                case "APPLOVIN-D":
                    if (incentivizedInterstitial != null) {
                        // A rewarded video is available.  Call the show method with the listeners you want to use.
                        // We will use the display listener to preload the next rewarded video when this one finishes.
                        incentivizedInterstitial.show(activity, new AppLovinAdRewardListener() {
                            @Override
                            public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
                                unlockreward = true;
                            }

                            @Override
                            public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

                            }

                            @Override
                            public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

                            }

                            @Override
                            public void validationRequestFailed(AppLovinAd ad, int errorCode) {

                            }

                            @Override
                            public void userDeclinedToViewAd(AppLovinAd ad) {

                            }
                        }, null, new AppLovinAdDisplayListener() {
                            @Override
                            public void adDisplayed(AppLovinAd appLovinAd) {
                                // A rewarded video is being displayed.
                            }

                            @Override
                            public void adHidden(AppLovinAd appLovinAd) {
                                // A rewarded video was closed.  Preload the next video now.  We won't use a load listener.
                                incentivizedInterstitial.preload(null);
                            }
                        });
                    }
                    break;
                case "IRON":
                    IronSource.showRewardedVideo(idBackupReward);
                    break;
                case "STARTAPP":
                    if (rewardedVideo.isReady()) {
                        rewardedVideo.showAd();
                    }
                    break;
                case "UNITY":
                    if (UnityAds.isReady (idBackupReward)) {
                        UnityAds.show (activity, idBackupReward);
                    }
                    break;
            }
        }
    }

    public static void ShowRewardApplovinMax(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {

        if (rewardedAd.isReady()) {
            rewardedAd.showAd();
            LoadRewardApplovinMax(activity, selecBackuptAds, idReward, idBackupReward);
        } else {
            LoadRewardApplovinMax(activity, selecBackuptAds, idReward, idBackupReward);
            switch (selecBackuptAds) {
                case "GOOGLE-ADS":
                case "ADMOB":
                    if (mRewardedAd != null) {
                        Activity activityContext = activity;
                        mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                unlockreward = true;
                            }
                        });
                    }
                    break;
                case "MOPUB":
                    MoPubRewardedAds.showRewardedAd(idBackupReward);
                    rewardedAdListener = new MoPubRewardedAdListener() {
                        @Override
                        public void onRewardedAdLoadSuccess(String adUnitId) {
                            // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
                        }

                        @Override
                        public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                        }

                        @Override
                        public void onRewardedAdStarted(String adUnitId) {
                            // Called when a rewarded ad starts playing.
                        }

                        @Override
                        public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                            //  Called when there is an error while attempting to show the ad.
                        }

                        @Override
                        public void onRewardedAdClicked(@NonNull String adUnitId) {
                            //  Called when a rewarded ad is clicked.
                        }

                        @Override
                        public void onRewardedAdClosed(String adUnitId) {
                            // Called when a rewarded ad is closed. At this point your application should resume.
                        }

                        @Override
                        public void onRewardedAdCompleted(Set<String> adUnitIds, MoPubReward reward) {
                            unlockreward = true;

                        }
                    };
                    MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
                    break;
                case "APPLOVIN-D":
                    if (incentivizedInterstitial != null) {
                        // A rewarded video is available.  Call the show method with the listeners you want to use.
                        // We will use the display listener to preload the next rewarded video when this one finishes.
                        incentivizedInterstitial.show(activity, new AppLovinAdRewardListener() {
                            @Override
                            public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
                                unlockreward = true;
                            }

                            @Override
                            public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

                            }

                            @Override
                            public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

                            }

                            @Override
                            public void validationRequestFailed(AppLovinAd ad, int errorCode) {

                            }

                            @Override
                            public void userDeclinedToViewAd(AppLovinAd ad) {

                            }
                        }, null, new AppLovinAdDisplayListener() {
                            @Override
                            public void adDisplayed(AppLovinAd appLovinAd) {
                                // A rewarded video is being displayed.
                            }

                            @Override
                            public void adHidden(AppLovinAd appLovinAd) {
                                // A rewarded video was closed.  Preload the next video now.  We won't use a load listener.
                                incentivizedInterstitial.preload(null);
                            }
                        });
                    }
                    break;
                case "IRON":
                    IronSource.showRewardedVideo(idBackupReward);
                    break;
                case "STARTAPP":
                    if (rewardedVideo.isReady()) {
                        rewardedVideo.showAd();
                    }
                    break;
                case "UNITY":
                    if (UnityAds.isReady (idBackupReward)) {
                        UnityAds.show (activity, idBackupReward);
                    }
                    break;
            }
        }
    }

    public static void ShowRewardApplovinDis(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {
        if (incentivizedInterstitial != null) {
            // A rewarded video is available.  Call the show method with the listeners you want to use.
            // We will use the display listener to preload the next rewarded video when this one finishes.
            incentivizedInterstitial.show(activity, new AppLovinAdRewardListener() {
                @Override
                public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
                    unlockreward = true;
                }

                @Override
                public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

                }

                @Override
                public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

                }

                @Override
                public void validationRequestFailed(AppLovinAd ad, int errorCode) {

                }

                @Override
                public void userDeclinedToViewAd(AppLovinAd ad) {

                }
            }, null, new AppLovinAdDisplayListener() {
                @Override
                public void adDisplayed(AppLovinAd appLovinAd) {
                    // A rewarded video is being displayed.
                }

                @Override
                public void adHidden(AppLovinAd appLovinAd) {
                    // A rewarded video was closed.  Preload the next video now.  We won't use a load listener.
                    incentivizedInterstitial.preload(null);
                }
            });
            LoadRewardApplovinDis(activity, selecBackuptAds, idReward, idBackupReward);
        } else {
            LoadRewardApplovinDis(activity, selecBackuptAds, idReward, idBackupReward);
            switch (selecBackuptAds) {
                case "GOOGLE-ADS":
                case "ADMOB":
                    if (mRewardedAd != null) {
                        Activity activityContext = activity;
                        mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                unlockreward = true;
                            }
                        });
                    }
                    break;
                case "MOPUB":
                    MoPubRewardedAds.showRewardedAd(idBackupReward);
                    rewardedAdListener = new MoPubRewardedAdListener() {
                        @Override
                        public void onRewardedAdLoadSuccess(String adUnitId) {
                            // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
                        }

                        @Override
                        public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                        }

                        @Override
                        public void onRewardedAdStarted(String adUnitId) {
                            // Called when a rewarded ad starts playing.
                        }

                        @Override
                        public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                            //  Called when there is an error while attempting to show the ad.
                        }

                        @Override
                        public void onRewardedAdClicked(@NonNull String adUnitId) {
                            //  Called when a rewarded ad is clicked.
                        }

                        @Override
                        public void onRewardedAdClosed(String adUnitId) {
                            // Called when a rewarded ad is closed. At this point your application should resume.
                        }

                        @Override
                        public void onRewardedAdCompleted(Set<String> adUnitIds, MoPubReward reward) {
                            unlockreward = true;

                        }
                    };
                    MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
                    break;
                case "APPLOVIN-M":
                    if (rewardedAd.isReady()) {
                        rewardedAd.showAd();
                    }
                    break;
                case "IRON":
                    IronSource.showRewardedVideo(idBackupReward);
                    break;
                case "STARTAPP":
                    if (rewardedVideo.isReady()) {
                        rewardedVideo.showAd();
                    }
                    break;
                case "UNITY":
                    if (UnityAds.isReady (idBackupReward)) {
                        UnityAds.show (activity, idBackupReward);
                    }
                    break;
            }
        }
    }

    public static void ShowRewardMopub(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {

        MoPubRewardedAds.showRewardedAd(idReward);
        rewardedAdListener = new MoPubRewardedAdListener() {
            @Override
            public void onRewardedAdLoadSuccess(String adUnitId) {
                // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
            }

            @Override
            public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
            }

            @Override
            public void onRewardedAdStarted(String adUnitId) {
                // Called when a rewarded ad starts playing.
            }

            @Override
            public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                switch (selecBackuptAds) {
                    case "GOOGLE-ADS":
                    case "ADMOB":
                        if (mRewardedAd != null) {
                            Activity activityContext = activity;
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    unlockreward = true;
                                }
                            });
                        }
                        break;
                    case "APPLOVIN-D":
                        if (incentivizedInterstitial != null) {
                            // A rewarded video is available.  Call the show method with the listeners you want to use.
                            // We will use the display listener to preload the next rewarded video when this one finishes.
                            incentivizedInterstitial.show(activity, new AppLovinAdRewardListener() {
                                @Override
                                public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
                                    unlockreward = true;
                                }

                                @Override
                                public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

                                }

                                @Override
                                public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

                                }

                                @Override
                                public void validationRequestFailed(AppLovinAd ad, int errorCode) {

                                }

                                @Override
                                public void userDeclinedToViewAd(AppLovinAd ad) {

                                }
                            }, null, new AppLovinAdDisplayListener() {
                                @Override
                                public void adDisplayed(AppLovinAd appLovinAd) {
                                    // A rewarded video is being displayed.
                                }

                                @Override
                                public void adHidden(AppLovinAd appLovinAd) {
                                    // A rewarded video was closed.  Preload the next video now.  We won't use a load listener.
                                    incentivizedInterstitial.preload(null);
                                }
                            });
                        }
                        break;
                    case "APPLOVIN-M":
                        if (rewardedAd.isReady()) {
                            rewardedAd.showAd();
                        }
                        break;
                    case "IRON":
                        IronSource.showRewardedVideo(idBackupReward);
                        break;
                    case "STARTAPP":
                        if (rewardedVideo.isReady()) {
                            rewardedVideo.showAd();
                        }
                        break;
                    case "UNITY":
                        if (UnityAds.isReady (idBackupReward)) {
                            UnityAds.show (activity, idBackupReward);
                        }
                        break;
                }
            }

            @Override
            public void onRewardedAdClicked(@NonNull String adUnitId) {
                //  Called when a rewarded ad is clicked.
            }

            @Override
            public void onRewardedAdClosed(String adUnitId) {
                // Called when a rewarded ad is closed. At this point your application should resume.
            }

            @Override
            public void onRewardedAdCompleted(Set<String> adUnitIds, MoPubReward reward) {
                unlockreward = true;

            }
        };
        MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
        LoadRewardMopub(activity, selecBackuptAds, idReward, idBackupReward);

    }

    public static void ShowRewardIron(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {
        IronSource.showRewardedVideo(idBackupReward);
        LoadRewardIron(activity, selecBackuptAds, idReward, idBackupReward);
    }

    public static void ShowRewardUnity(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {
        if (UnityAds.isReady (idReward)) {
            UnityAds.show(activity, idReward);
            LoadRewardUnity(activity,selecBackuptAds,idReward,idBackupReward);
        }
    }
    public static void ShowRewardStartApp(Activity activity, String selecBackuptAds, String idReward, String idBackupReward) {
        if (rewardedVideo.isReady()) {
            rewardedVideo.showAd();
            LoadRewardStartApp(activity, selecBackuptAds, idReward, idBackupReward);
        } else {
            LoadRewardStartApp(activity, selecBackuptAds, idReward, idBackupReward);
            switch (selecBackuptAds) {
                case "APPLOVIN-M":
                    if (rewardedAd.isReady()) {
                        rewardedAd.showAd();
                    }
                    break;
                case "MOPUB":
                    MoPubRewardedAds.showRewardedAd(idBackupReward);
                    rewardedAdListener = new MoPubRewardedAdListener() {
                        @Override
                        public void onRewardedAdLoadSuccess(String adUnitId) {
                            // Called when the ad for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedAds.showRewardedAd() to show the ad.
                        }

                        @Override
                        public void onRewardedAdLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                        }

                        @Override
                        public void onRewardedAdStarted(String adUnitId) {
                            // Called when a rewarded ad starts playing.
                        }

                        @Override
                        public void onRewardedAdShowError(String adUnitId, MoPubErrorCode errorCode) {
                            //  Called when there is an error while attempting to show the ad.
                        }

                        @Override
                        public void onRewardedAdClicked(@NonNull String adUnitId) {
                            //  Called when a rewarded ad is clicked.
                        }

                        @Override
                        public void onRewardedAdClosed(String adUnitId) {
                            // Called when a rewarded ad is closed. At this point your application should resume.
                        }

                        @Override
                        public void onRewardedAdCompleted(Set<String> adUnitIds, MoPubReward reward) {
                            unlockreward = true;

                        }
                    };
                    MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);
                    break;
                case "APPLOVIN-D":
                    if (incentivizedInterstitial != null) {
                        // A rewarded video is available.  Call the show method with the listeners you want to use.
                        // We will use the display listener to preload the next rewarded video when this one finishes.
                        incentivizedInterstitial.show(activity, new AppLovinAdRewardListener() {
                            @Override
                            public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
                                unlockreward = true;
                            }

                            @Override
                            public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

                            }

                            @Override
                            public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

                            }

                            @Override
                            public void validationRequestFailed(AppLovinAd ad, int errorCode) {

                            }

                            @Override
                            public void userDeclinedToViewAd(AppLovinAd ad) {

                            }
                        }, null, new AppLovinAdDisplayListener() {
                            @Override
                            public void adDisplayed(AppLovinAd appLovinAd) {
                                // A rewarded video is being displayed.
                            }

                            @Override
                            public void adHidden(AppLovinAd appLovinAd) {
                                // A rewarded video was closed.  Preload the next video now.  We won't use a load listener.
                                incentivizedInterstitial.preload(null);
                            }
                        });
                    }
                    break;
                case "IRON":
                    IronSource.showRewardedVideo(idBackupReward);
                    break;
                case "GOOGLE-ADS":
                case "ADMOB":
                    if (mRewardedAd != null) {
                        Activity activityContext = activity;
                        mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                unlockreward = true;
                                LoadRewardAdmob(activity, selecBackuptAds, idReward, idBackupReward);
                            }
                        });
                    }
                    break;
                case "UNITY":
                    if (UnityAds.isReady (idBackupReward)) {
                        UnityAds.show (activity, idBackupReward);
                    }
                    break;
            }
        }
    }
}
