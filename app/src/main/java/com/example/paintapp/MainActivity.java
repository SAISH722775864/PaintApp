package com.example.paintapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView canvasImageView;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private Path path;
    private float currentStrokeWidth = 10f; // Default stroke width

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasImageView = findViewById(R.id.canvasImageView);
        Button clearButton = findViewById(R.id.clearButton);
        Button increaseSizeButton = findViewById(R.id.increaseSizeButton);
        Button decreaseSizeButton = findViewById(R.id.decreaseSizeButton);

        // Color buttons
        Button redButton = findViewById(R.id.redButton);
        Button greenButton = findViewById(R.id.greenButton);
        Button blueButton = findViewById(R.id.blueButton);
        Button blackButton = findViewById(R.id.blackButton);

        setupDrawing();

        // Handle touch events for drawing
        canvasImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        path.moveTo(x, y);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        path.lineTo(x, y);
                        canvas.drawPath(path, paint); // Draw in real time
                        path.moveTo(x, y); // Smooth out the drawing
                        break;

                    case MotionEvent.ACTION_UP:
                        canvas.drawPath(path, paint); // Ensure final segment is drawn
                        path.reset();
                        break;
                }

                canvasImageView.invalidate(); // Refresh the view
                return true;
            }
        });

        // Clear button
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCanvas();
            }
        });

        // Increase line size button
        increaseSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStrokeWidth += 5;
                paint.setStrokeWidth(currentStrokeWidth);
            }
        });

        // Decrease line size button
        decreaseSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStrokeWidth > 5) {
                    currentStrokeWidth -= 5;
                    paint.setStrokeWidth(currentStrokeWidth);
                }
            }
        });

        // Color selection buttons
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.setColor(0xFFFF0000); // Red
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.setColor(0xFF00FF00); // Green
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.setColor(0xFF0000FF); // Blue
            }
        });

        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.setColor(0xFF000000); // Black
            }
        });
    }

    private void setupDrawing() {
        // Create a blank bitmap and canvas
        bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        // Initialize the paint object
        paint = new Paint();
        paint.setColor(0xFF000000); // Default color: black
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(currentStrokeWidth);

        // Initialize the path for drawing
        path = new Path();

        // Set the bitmap to the ImageView
        canvasImageView.setImageBitmap(bitmap);
    }

    private void clearCanvas() {
        // Clear the canvas with a white background
        canvas.drawColor(0xFFFFFFFF);
        canvasImageView.invalidate(); // Refresh the view
    }
}
