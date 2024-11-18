package com.example.petmate.core.navigator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("keep_state_fragment")
class KeepStateFragmentNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()

        val initialNavigate = hideCurrentFragment(transaction)
        val fragment = findOrCreateFragment(destination, tag)

        showAndCommitFragment(transaction, fragment)

        return if (initialNavigate) destination else null
    }

    // 현재 보여지는 fragment를 숨김
    private fun hideCurrentFragment(transaction: FragmentTransaction): Boolean {
        val currentFragment = manager.primaryNavigationFragment
        return if (currentFragment != null) {
            transaction.hide(currentFragment)
            false
        } else {
            true
        }
    }

    // fragment를 찾거나 새로운 fragment를 생성
    private fun findOrCreateFragment(
        destination: Destination,
        tag: String
    ): androidx.fragment.app.Fragment {
        return manager.findFragmentByTag(tag) ?: createFragment(destination, tag)
    }

    // 새로운 fragment를 생성하여 transaction에 추가
    private fun createFragment(
        destination: Destination,
        tag: String
    ): androidx.fragment.app.Fragment {
        val className = destination.className
        val fragment = manager.fragmentFactory.instantiate(context.classLoader, className)
        manager.beginTransaction().add(containerId, fragment, tag).commitNow()
        return fragment
    }

    // fragment를 보여주고 transaction을 커밋
    private fun showAndCommitFragment(
        transaction: FragmentTransaction,
        fragment: androidx.fragment.app.Fragment
    ) {
        transaction.show(fragment)
        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commitNow()
    }
}
