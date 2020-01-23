from __future__ import print_function
import PIL
from PIL import Image as Im
import numpy as np



"""
yksinkertainen kuvan ascii merkeiksi muuttaja. 
"""

im = Im.open("homer.png") 
print(im.format, im.size, im.mode)

im.resize((100, 100)) #muutetaan sopivan kokoiseksi


chars = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$"

"""
tehdään pikseleistä matriisi
"""
def pikselimatriisi(img, height):
    img.thumbnail((height, 200))
    pixels = list(img.getdata())
    return [pixels[i:i+img.width] for i in range(0, len(pixels), img.width)]


"""
muuttaa (x, y, z) muotoisen funktiolla (R + G + B) / 3 = joksikin int arvoksi x
"""
def intensity_matrix(matriisi):
    pixel_matriisi = []
    for row in pixels:
        pixel_row = []
        for p in row:
            intensity = (int(p[0]) + int(p[1]) + int(p[2]) / 3)
            pixel_row.append(intensity)
        pixel_matriisi.append(pixel_row)
    return pixel_matriisi


"""
normalisoidaan matriisin pikselien arvot
"""
def normalisoi(matriisi):
    normalisoitu_mat = []
    max_pixel = max(map(max, matriisi))
    min_pixel = min(map(min, matriisi))
    for row in matriisi:
        uusi_row = []
        for p in row:
            r = MAKSI * (p - min_pixel) / float(max_pixel - min_pixel)
            uusi_row.append(r)
        normalisoitu_mat.append(uusi_row)
    return normalisoitu_mat


"""
pikselien arvot vastaamaan merkkejä
"""
def map_chars(matriisi, merkit):
    ascii_mat = []
    for row in matriisi:
        ascii_row = []
        for p in row:
            ascii_row.append(merkit[int(p/MAKSI * len(merkit)) - 1])
        ascii_mat.append(ascii_row)
    return ascii_mat

        
luku = 9.153846154
MAKSI = 255

pixels = pikselimatriisi(im, 1000)

norm_mat = normalisoi(intensity_matrix(pixels))
asciina = map_chars(norm_mat, chars)

"""
printataan konsoliin koko ascii matriisi
"""
def print_ascii_matrix(ascii_matrix):
    for row in ascii_matrix:
        line = [p+p+p for p in row]
        print("".join(line))


print_ascii_matrix(asciina)
