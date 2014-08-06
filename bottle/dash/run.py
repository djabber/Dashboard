#!/usr/bin/env python
import sys, os
sys.path.append(os.path.dirname(__file__))
from app.static.views import app

app.run()
