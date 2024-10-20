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
        return "GEOMETRY\n\n" +
                "What is Geometry?\n" +
                "Geometry is the branch of mathematics that deals with the study of shapes, sizes, relative positions of figures, and the properties of space. It has applications in numerous fields, including architecture, engineering, and physics.\n\n" +
                "Key Concepts in Geometry:\n\n" +
                "1. Points: A point is a precise location in space. It has no size, only position.\n\n" +
                "2. Lines: A line is a straight path extending infinitely in both directions. It has length but no width.\n" +
                "   - Line Segment: A part of a line with two endpoints.\n" +
                "   - Ray: A part of a line with one endpoint, extending infinitely in one direction.\n\n" +
                "3. Angles: An angle is formed by two rays sharing a common endpoint (vertex).\n" +
                "   - Acute Angle: Measures less than 90°\n" +
                "   - Right Angle: Measures exactly 90°\n" +
                "   - Obtuse Angle: Measures more than 90° but less than 180°\n" +
                "   - Straight Angle: Measures exactly 180°\n\n" +
                "4. Polygons: Closed figures made up of line segments.\n" +
                "   - Triangle: 3 sides\n" +
                "   - Quadrilateral: 4 sides (e.g., Square, Rectangle, Rhombus, Trapezoid)\n" +
                "   - Pentagon: 5 sides\n" +
                "   - Hexagon: 6 sides\n" +
                "   - Octagon: 8 sides\n\n" +
                "5. Circles: A set of points equidistant from a central point.\n" +
                "   - Radius: Distance from the center to any point on the circle\n" +
                "   - Diameter: A line segment passing through the center, with endpoints on the circle\n" +
                "   - Circumference: The distance around the circle\n\n" +
                "6. Three-dimensional Shapes:\n" +
                "   - Cube: 6 square faces\n" +
                "   - Sphere: All points equidistant from a center point\n" +
                "   - Cylinder: Circular base with curved surface\n" +
                "   - Cone: Circular base with a point at the top\n" +
                "   - Pyramid: Polygonal base with triangular faces meeting at a point\n\n" +
                "Important Geometry Formulas:\n\n" +
                "1. Areas:\n" +
                "   - Rectangle: A = length × width\n" +
                "   - Square: A = side²\n" +
                "   - Triangle: A = ½ × base × height\n" +
                "   - Circle: A = πr² (where r is the radius)\n" +
                "   - Trapezoid: A = ½(b₁ + b₂)h (where b₁ and b₂ are parallel sides and h is height)\n\n" +
                "2. Perimeters:\n" +
                "   - Rectangle: P = 2(length + width)\n" +
                "   - Square: P = 4 × side\n" +
                "   - Circle (Circumference): C = 2πr or C = πd (where d is diameter)\n\n" +
                "3. Volumes:\n" +
                "   - Cube: V = side³\n" +
                "   - Rectangular Prism: V = length × width × height\n" +
                "   - Sphere: V = (4/3)πr³\n" +
                "   - Cylinder: V = πr²h (where h is height)\n" +
                "   - Cone: V = (1/3)πr²h\n\n" +
                "4. Surface Areas:\n" +
                "   - Cube: SA = 6 × side²\n" +
                "   - Sphere: SA = 4πr²\n" +
                "   - Cylinder: SA = 2πr² + 2πrh\n\n" +
                "5. Pythagorean Theorem:\n" +
                "   For a right triangle with sides a, b, and hypotenuse c:\n" +
                "   a² + b² = c²\n\n" +
                "6. Trigonometric Ratios in Right Triangles:\n" +
                "   sin θ = opposite / hypotenuse\n" +
                "   cos θ = adjacent / hypotenuse\n" +
                "   tan θ = opposite / adjacent\n\n" +
                "Practical Applications of Geometry:\n" +
                "- Architecture: Designing buildings and structures\n" +
                "- Engineering: Creating machines and tools\n" +
                "- Computer Graphics: Rendering 3D images and animations\n" +
                "- Navigation: GPS and mapping systems\n" +
                "- Art: Creating perspective in drawings and paintings\n" +
                "- Physics: Understanding motion and forces\n\n" +
                "Remember, geometry is not just about memorizing formulas, but understanding spatial relationships and logical reasoning. Practice solving problems and visualizing shapes to improve your geometry skills!";
    }
}