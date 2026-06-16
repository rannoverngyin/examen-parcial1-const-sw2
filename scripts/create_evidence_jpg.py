from PIL import Image, ImageDraw, ImageFont
from pathlib import Path
import os

text = '''Reporte de evidencia de pruebas

Comando ejecutado: ./mvnw.cmd test

Resultados:
- ProductoControllerIntegrationTest: tests=2 errors=0 failures=0
- ProductoServiceConcurrencyTest: tests=1 errors=0 failures=0
- ProductoApiConcurrencyTest: tests=1 errors=0 failures=0

Endpoint verificado:
GET /productos
POST /productos?nombre=Teclado

Resultado: BUILD SUCCESS'''

lines = text.splitlines()
font = ImageFont.load_default()

# Calculate image size
dummy = Image.new('RGB', (1, 1))
draw = ImageDraw.Draw(dummy)
line_heights = [draw.textbbox((0, 0), line, font=font)[3] - draw.textbbox((0, 0), line, font=font)[1] for line in lines]
width = max(draw.textlength(line, font=font) for line in lines) + 40
height = sum(line_heights) + 20 + len(lines) * 6

image = Image.new('RGB', (int(width), int(height)), 'white')
draw = ImageDraw.Draw(image)
y = 20
for line, line_height in zip(lines, line_heights):
    draw.text((20, y), line, fill='black', font=font)
    y += line_height + 6

out_path = Path('docs') / 'evidencia-pruebas-sesion11.jpg'
out_path.parent.mkdir(parents=True, exist_ok=True)
image.save(out_path, 'JPEG')
print(out_path)
