#version 400 core

//in vec3 colour; // Input from the vertex shader

out vec4 out_Color;

in vec2 out_texCords;

uniform sampler2D textureSampler;

void main() {
    out_Color = texture(textureSampler, out_texCords);
}
