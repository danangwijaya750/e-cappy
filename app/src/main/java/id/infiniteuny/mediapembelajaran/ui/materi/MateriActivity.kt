package id.infiniteuny.mediapembelajaran.ui.materi

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.MaterialModel
import kotlinx.android.synthetic.main.activity_materi.rv_materi
import kotlinx.android.synthetic.main.activity_setting.btn_back

class MateriActivity : AppCompatActivity(), MateriView {


    private lateinit var presenter: MateriPresenter

    private val dataMaterials = mutableListOf<MaterialModel.Data>()
    private val rvAdapter = object : RvAdapter<Any>(dataMaterials) {
        override fun layoutId(position: Int, obj: Any): Int = R.layout.item_layout

        override fun viewHolder(view: View, viewType: Int): ViewHolder = MateriVH(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_materi)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()

        rv_materi.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@MateriActivity)
        }

        presenter = MateriPresenter(this, this)

        presenter.getMaterials()
        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun isLoading(state: Boolean) {
    }

    override fun isError(msg: String) {
    }

    override fun showMaterials(data: List<MaterialModel.Data>) {
        dataMaterials.clear()
        dataMaterials.addAll(data)
        rvAdapter.notifyDataSetChanged()
    }
}
