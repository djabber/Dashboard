import yaml

# Class processes settings in yaml files

class SettingsProcessor:


	def __init__(self): pass

	
	def getSettings(self, yaml_file):

		f = open(yaml_file)
		dataMap = yaml.safe_load(f)
		f.close()

		return dataMap
