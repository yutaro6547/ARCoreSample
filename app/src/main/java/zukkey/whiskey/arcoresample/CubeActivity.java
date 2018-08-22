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
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class CubeActivity extends AppCompatActivity {
  private ArFragment arFragment;
  private ModelRenderable blueSquareRenderable;


  public static Intent createIntent(Context context) {
    return new Intent(context, CubeActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cube);
    arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

    MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
        .thenAccept(
            material -> {
              blueSquareRenderable =
                  ShapeFactory.makeCube(new Vector3(0.2f, 0.15f, 0.2f), new Vector3(0.0f, 0.15f, 0.0f), material);
            }
        );

    arFragment.setOnTapArPlaneListener(
        (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
          if (blueSquareRenderable == null) {
            return;
          }

          Anchor anchor = hitResult.createAnchor();
          AnchorNode anchorNode = new AnchorNode(anchor);
          anchorNode.setParent(arFragment.getArSceneView().getScene());

          TransformableNode blueSquare = new TransformableNode(arFragment.getTransformationSystem());
          blueSquare.setParent(anchorNode);
          blueSquare.setRenderable(blueSquareRenderable);
          blueSquare.select();
          blueSquare.getRotationController();
        });
  }
}
