package com.quan.lam.yelpfusiondemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.quan.lam.yelpfusiondemo.data.Business
import com.quan.lam.yelpfusiondemo.data.YelpBusinessResponse
import com.quan.lam.yelpfusiondemo.data.YelpFusionAPI
import com.quan.lam.yelpfusiondemo.ui.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner


/**
 * MovieReviewListViewModel Test
 * Different action will be perform and we will test the final state of the ViewModel
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(YelpFusionAPI::class)
@PowerMockIgnore("javax.net.ssl.*")
class MainViewModelTest {
    lateinit var testViewModel: MainViewModel
    companion object {
        @BeforeClass @JvmStatic fun setup() {
            // things to execute once and keep around for the class
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @AfterClass
        @JvmStatic fun teardown() {
            // clean up after this class, leave nothing dirty behind
        }
    }

    @Rule
    private var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        PowerMockito.mockStatic(YelpFusionAPI::class.java)
        PowerMockito.mockStatic(YelpFusionAPI.Companion::class.java)
        val mockAPI = mock(YelpFusionAPI::class.java)

        val mockBusinessResponse = ArrayList<Business>(10)
        mockBusinessResponse.add(Business(name = "Dummy"))
        Mockito.`when`(mockAPI.searchBusinesses(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
            .thenReturn(Observable.just(YelpBusinessResponse(10, mockBusinessResponse)))
        YelpFusionAPI.setAPI(mockAPI)
    }

    @Test
    fun construction_test() {
        testViewModel = MainViewModel()
        assert(testViewModel.businessList.size == 0)
    }

    @Test
    fun searchBusinesses_test() {
        testViewModel = MainViewModel()
        testViewModel.getBusinesses("Dummy", "Dummy", true)
        Thread.sleep(1000)
        assert(testViewModel.businessList.size == 1)
    }

}