#version 400 core

in vec3 colour; // Input from the vertex shader
out vec4 out_Color;


void main() {
    out_Color = vec4(colour, 1.0);
}
