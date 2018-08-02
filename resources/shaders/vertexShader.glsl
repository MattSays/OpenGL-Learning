#version 400 core

in vec3 position; // The VAO vertex
out vec3 colour; // Output to Fragment Shader

void main() {

    gl_Position = vec4(position, 1.0); // Tells to the gpu the correct vertex position

    colour = vec3(position.x + 0.5, 1.0, position.y + 0.5 );

}
