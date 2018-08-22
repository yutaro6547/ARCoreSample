package zukkey.whiskey.arcoresample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ViewActivity extends AppCompatActivity {
  private ArFragment arFragment;
  private ViewRenderable viewRenderable;

  public static Intent createIntent(Context context) {
    return new Intent(context, ViewActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view);
    arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

    ViewRenderable.builder()
        .setView(this, R.layout.item_image)
        .build()
        .thenAccept(renderable -> viewRenderable = renderable);

    arFragment.setOnTapArPlaneListener(
        (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
          if (viewRenderable == null) {
            return;
          }

          Anchor anchor = hitResult.createAnchor();
          AnchorNode anchorNode = new AnchorNode(anchor);
          anchorNode.setParent(arFragment.getArSceneView().getScene());

          TransformableNode view = new TransformableNode(arFragment.getTransformationSystem());
          view.setParent(anchorNode);
          view.setRenderable(viewRenderable);
          view.getRotationController();
          view.getScaleController();
          view.getTranslationController();
          view.select();

        });
  }
}
