package zukkey.whiskey.arcoresample;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable andyRenderable;
    private ModelRenderable redSphereRenderable;
    private ModelRenderable blueSquareRenderable;
    private ModelRenderable yellowCylinderRenderable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build()
                .thenAccept(renderable -> andyRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> {
                            redSphereRenderable =
                                    ShapeFactory.makeSphere(0.1f, new Vector3(0.0f, 0.15f, 0.0f), material); });

        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> {
                            blueSquareRenderable =
                                    ShapeFactory.makeCube(new Vector3(0.2f, 0.15f, 0.2f), new Vector3(0.0f, 0.15f, 0.0f), material);
                        }
                );

        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.YELLOW))
                .thenAccept(
                        material -> {
                            yellowCylinderRenderable =
                                    ShapeFactory.makeCylinder(0.1f, 0.3f, new Vector3(0.0f, 0.15f, 0.0f), material);
                        }
                );

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (andyRenderable == null) {
                        return;
                    }

                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    Random rnd = new Random();
                    Integer number = rnd.nextInt();

                    if (number % 2 == 0) {
                        TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
                        andy.setParent(anchorNode);
                        andy.setRenderable(andyRenderable);
                        andy.getRotationController();
                        andy.getScaleController();
                        andy.getTranslationController();
                        andy.select();
                    } else if (number % 3 == 0) {
                        TransformableNode redBall = new TransformableNode(arFragment.getTransformationSystem());
                        redBall.setParent(anchorNode);
                        redBall.setRenderable(redSphereRenderable);
                        redBall.select();
                        redBall.getRotationController();
                    } else if (number % 5 == 0){
                        TransformableNode blueSquare = new TransformableNode(arFragment.getTransformationSystem());
                        blueSquare.setParent(anchorNode);
                        blueSquare.setRenderable(blueSquareRenderable);
                        blueSquare.select();
                        blueSquare.getRotationController();
                    } else  {
                        TransformableNode yellowCylinder = new TransformableNode(arFragment.getTransformationSystem());
                        yellowCylinder.setParent(anchorNode);
                        yellowCylinder.setRenderable(yellowCylinderRenderable);
                        yellowCylinder.select();
                        yellowCylinder.getRotationController();
                    }
                });
    }
}
