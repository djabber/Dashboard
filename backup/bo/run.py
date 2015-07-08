#!/usr/bin/env python
from app.static.views import app
import sys, os, subprocess


sys.path.append(os.path.dirname(__file__))
app.run()
