#version 400 core

in vec2 texCords;
in vec3 position; // The VAO vertex

// out vec3 colour; // Colour Output to Fragment Shader
out vec2 out_texCords;

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;

void main() {

    gl_Position =  cameraMatrix * modelMatrix * vec4(position, 1.0); // Tells to the gpu the correct vertex position
    out_texCords = texCords;

    //colour = vec3(position.x + 0.5, 1.0, position.y + 0.5 );

}
