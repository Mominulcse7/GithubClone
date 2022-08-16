package com.mominulcse7.githubclone

import androidx.navigation.testing.TestNavHostController
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class FragmentTest {

    val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @UiThreadTest
    @Test
    fun testGetCurrentBackStackEntry() {
        navController.setGraph(R.navigation.nav_graph)
        assertThat(navController.currentBackStackEntry?.destination?.id ?: 0)
            .isEqualTo(R.id.repoListFragment)
    }

    @UiThreadTest
    @Test
    fun testGetCurrentBackStackEntryEmptyBackStack() {
        assertThat(navController.currentBackStackEntry).isNull()
    }

    @UiThreadTest
    @Test
    fun testGetPreviousBackStackEntry() {
        navController.setGraph(R.navigation.nav_graph)
        navController.navigate(R.id.repoDetailsFragment)
        assertThat(navController.previousBackStackEntry?.destination?.id ?: 0)
            .isEqualTo(R.id.repoListFragment)
    }

    @UiThreadTest
    @Test
    fun testGetPreviousBackStackEntryEmptyBackStack() {
        navController.setGraph(R.navigation.nav_graph)
        assertThat(navController.previousBackStackEntry).isNull()
    }

    @UiThreadTest
    @Test
    fun testStartDestination() {
        navController.setGraph(R.navigation.nav_graph)
        assertThat(navController.currentDestination?.id ?: 0).isEqualTo(R.id.repoListFragment)
    }


}