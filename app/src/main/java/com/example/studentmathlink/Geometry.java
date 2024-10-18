package com.example.studentmathlink;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Geometry extends AppCompatActivity {
    private TextView geometryContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry);
        geometryContent = findViewById(R.id.geometryContent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String content = getGeometryContent();
        geometryContent.setText(content);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String getGeometryContent() {
        return "What is Geometry?\n" +
                "Geometry is the branch of mathematics that deals with shapes, angles, dimensions, and sizes of objects in the real world.\n" +
                "\nBranches of Geometry:\n" +
                "1. Algebraic Geometry\n" +
                "2. Discrete Geometry\n" +
                "3. Differential Geometry\n" +
                "4. Euclidean Geometry\n" +
                "5. Convex Geometry\n" +
                "6. Topology\n" +
                "\nKey Concepts in Geometry:\n" +
                "1. Points\n" +
                "2. Lines\n" +
                "3. Angles\n" +
                "4. Circles\n" +
                "5. Polygons\n" +
                "6. Triangles\n" +
                "7. Area and Perimeter\n" +
                "\nPoints: A point is a precise location or place on a plane. It has no dimension but only position.\n" +
                "Lines: A line is straight, has no thickness, and extends infinitely in both directions.\n" +
                "Angles: An angle is formed when two lines meet at a point.\n" +
                "Circles: A circle is a set of points on a plane that are equidistant from a given point, called the center.\n" +
                "\nImportant Geometry Formulas:\n" +
                "1. Area of a Triangle = ½ × base × height\n" +
                "2. Circumference of a Circle = 2πr\n" +
                "3. Area of a Circle = πr²\n" +
                "4. Perimeter of a Rectangle = 2(l + w)\n";
    }
}
