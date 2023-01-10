package com.example.stackview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stackview.adapter.PlansAdapter
import com.example.stackview.databinding.ActivityMainBinding
import com.example.stackview.model.Plans
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val plansAdapter by lazy { PlansAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val bottomSheetDialog = BottomSheetDialog(
            this@MainActivity, R.style.BottomSheetDialogTheme
        )

        binding.closeIcon.setOnClickListener {
            binding.openViewStack.visibility = View.VISIBLE
            binding.closeIcon.visibility = View.INVISIBLE
            binding.infoIcon.visibility = View.INVISIBLE
        }

        val showBottomSheet = binding.openViewStack
        showBottomSheet.setOnClickListener {
            binding.closeIcon.visibility = View.VISIBLE
            binding.infoIcon.visibility = View.VISIBLE
            binding.openViewStack.visibility = View.INVISIBLE
            val bottomSheetView: View = LayoutInflater.from(applicationContext)
                .inflate(
                    R.layout.bottom_sheet,
                    findViewById<View>(R.id.bottom_sheet_container) as? ConstraintLayout
                )

            val button: TextView = bottomSheetView.findViewById(R.id.first_bottom_sheet_cta)
            val firstToggleIcon = bottomSheetView.findViewById<ImageView>(R.id.toggle_icon)
            val titleText = bottomSheetView.findViewById<TextView>(R.id.first_title)
            val subtitleText = bottomSheetView.findViewById<TextView>(R.id.first_subtitle)
            val bgLayout = bottomSheetView.findViewById<LinearLayout>(R.id.background_detail_layout)
            button.setOnClickListener {
                openSecondBottomSheet(bottomSheetView)
                firstToggleIcon.visibility = View.VISIBLE
                titleText.visibility = View.INVISIBLE
                subtitleText.visibility = View.INVISIBLE
                bgLayout.visibility = View.VISIBLE
            }

            bottomSheetDialog.setOnDismissListener {
                binding.closeIcon.visibility = View.INVISIBLE
                binding.infoIcon.visibility = View.INVISIBLE
                binding.openViewStack.visibility = View.VISIBLE
            }

            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.behavior.expandedOffset = 100
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
    }

    private fun openSecondBottomSheet(bottomSheetView: View) {
        val bottomSheetDialogTwo = BottomSheetDialog(
            this@MainActivity, R.style.BottomSheetDialogTheme
        )
        val bottomSheetViewTwo: View = LayoutInflater.from(applicationContext)
            .inflate(
                R.layout.bottom_sheet_two,
                findViewById<View>(R.id.bottom_sheet_container_two) as? ConstraintLayout
            )

        val recyclerView = bottomSheetViewTwo.findViewById<RecyclerView>(R.id.plans_recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val list = mutableListOf(
            Plans(1, 12, 4247, false, resources.getColor(R.color.square_card_bg_one)),
            Plans(2, 9, 5580, true, resources.getColor(R.color.square_card_bg_two)),
            Plans(3, 6, 8260, false, resources.getColor(R.color.square_card_bg_three)),
            Plans(4, 3, 16520, false, resources.getColor(R.color.square_card_bg_four))
        )

        recyclerView.adapter = plansAdapter
        plansAdapter.submitList(list)

        val button: TextView = bottomSheetViewTwo.findViewById(R.id.second_bottom_sheet_cta)
        val secondToggleIcon = bottomSheetViewTwo.findViewById<ImageView>(R.id.toggle_icon_two)
        val titleTextTwo = bottomSheetViewTwo.findViewById<TextView>(R.id.second_title)
        val subtitleTextTwo = bottomSheetViewTwo.findViewById<TextView>(R.id.second_subtitle)
        val bgLayout =
            bottomSheetViewTwo.findViewById<LinearLayout>(R.id.second_background_detail_layout)
        button.setOnClickListener {
            openThirdBottomSheet(bottomSheetViewTwo)
            titleTextTwo.visibility = View.INVISIBLE
            subtitleTextTwo.visibility = View.INVISIBLE
            bgLayout.visibility = View.VISIBLE
            secondToggleIcon.visibility = View.VISIBLE
        }

        bottomSheetDialogTwo.setOnDismissListener {
            val firstToggleIcon = bottomSheetView.findViewById<ImageView>(R.id.toggle_icon)
            val titleText = bottomSheetView.findViewById<TextView>(R.id.first_title)
            val subtitleText = bottomSheetView.findViewById<TextView>(R.id.first_subtitle)
            val bgLayoutOne =
                bottomSheetView.findViewById<LinearLayout>(R.id.background_detail_layout)

            titleText.visibility = View.VISIBLE
            subtitleText.visibility = View.VISIBLE
            bgLayoutOne.visibility = View.INVISIBLE
            firstToggleIcon.visibility = View.INVISIBLE
        }

        bottomSheetDialogTwo.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialogTwo.setContentView(bottomSheetViewTwo)
        bottomSheetDialogTwo.show()
    }

    private fun openThirdBottomSheet(bottomSheetViewTwo: View) {
        val bottomSheetDialogThree = BottomSheetDialog(
            this@MainActivity, R.style.BottomSheetDialogTheme
        )
        val bottomSheetViewThree: View = LayoutInflater.from(applicationContext)
            .inflate(
                R.layout.bottom_sheet_three,
                findViewById<View>(R.id.bottom_sheet_container_three) as? ConstraintLayout
            )

        bottomSheetDialogThree.setOnDismissListener {
            val secondToggleIcon = bottomSheetViewTwo.findViewById<ImageView>(R.id.toggle_icon_two)
            val titleTextTwo = bottomSheetViewTwo.findViewById<TextView>(R.id.second_title)
            val subtitleTextTwo = bottomSheetViewTwo.findViewById<TextView>(R.id.second_subtitle)
            val bgLayout =
                bottomSheetViewTwo.findViewById<LinearLayout>(R.id.second_background_detail_layout)

            titleTextTwo.visibility = View.VISIBLE
            subtitleTextTwo.visibility = View.VISIBLE
            bgLayout.visibility = View.INVISIBLE
            secondToggleIcon.visibility = View.INVISIBLE
        }
        bottomSheetDialogThree.setContentView(bottomSheetViewThree)
        bottomSheetDialogThree.show()
    }
}