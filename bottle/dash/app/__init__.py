from bottle import Bottle
from settings.mysql_connector import *
from settings.settings_processor import *
from src.my_ping import *
from src.ping import *
from src.server import *
from src.sys_info_processor import *


app = Bottle(__name__)
from app import views
