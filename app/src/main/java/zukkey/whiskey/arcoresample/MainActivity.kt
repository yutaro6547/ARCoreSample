package zukkey.whiskey.arcoresample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import zukkey.whiskey.arcoresample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val binding by lazy {
    DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.transition3d.setOnClickListener {
      startActivity(ModelActivity.createIntent(this))
    }

    binding.transitionView.setOnClickListener {
      startActivity(ViewActivity.createIntent(this))
    }

    binding.transitionBall.setOnClickListener {
      startActivity(BallActivity.createIntent(this))
    }

    binding.transitionCube.setOnClickListener {
      startActivity(CubeActivity.createIntent(this))
    }

    binding.transitionCylinder.setOnClickListener {
      startActivity(CylinderActivity.createIntent(this))
    }
  }
}
