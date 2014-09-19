using System;
using System.Collections;
using System.Reflection;

namespace sf.net.lpdnet.utils
{
	/// <summary>
	/// .NET framework does not support support object.clone() as java does. This class tries to
	/// clone an object using reflection. It will fail if the object to clone does not suppoert argumentless constructors.
	/// In order for the object be cloned successfully each object public fields must implement the IClonable interface.
	/// </summary>
	public class ObjectUtil
	{
		/// <summary>
		/// Tries to make a deep copy of an object. The object to clone must support argumentless constructors.
		/// All object public feilds must implemet IClonable interface.
		/// </summary>
		/// <param name="obj">Object to clone</param>
		/// <returns>Clone copy</returns>
		public static Object cloneObject(Object obj)
		{
			int j = 0;
			int i = 0;

			//First we create an instance of this specific type.
			object newObj = Activator.CreateInstance(obj.GetType());

			BindingFlags bindingFlags = BindingFlags.Public | BindingFlags.NonPublic
			                            | BindingFlags.Instance | BindingFlags.Static
			                            | BindingFlags.NonPublic | BindingFlags.DeclaredOnly;

			//We get the array of fields for the new type instance.
			FieldInfo[] fields = newObj.GetType().GetFields(bindingFlags);

			foreach (FieldInfo fieldInfo in obj.GetType().GetFields(bindingFlags))
			{
				//We query if the fiels support the ICloneable interface.
				bool isCloneable = fieldInfo.FieldType.GetInterface("ICloneable", true) != null ? true : false;

				if (isCloneable)
				{
					//Getting the ICloneable interface from the object.
					ICloneable clone = (ICloneable) fieldInfo.GetValue(obj);

					if (clone != null)
					{
						//We use the clone method to set the new value to the field.
						fields[i].SetValue(newObj, clone.Clone());
					}
				}
				else
				{
					// If the field doesn't support the ICloneable 
					// interface then just set it.
					fields[i].SetValue(newObj, fieldInfo.GetValue(obj));
					//throw new CloneNotSupportedException(fi.FieldType.FullName + " does not implement IClonable.");
				}

				//Now we check if the object support the 
				//IEnumerable interface, so if it does
				//we need to enumerate all its items and check if 
				//they support the ICloneable interface.
				Type IEnumerableType = fieldInfo.FieldType.GetInterface("IEnumerable", true);
				if (IEnumerableType != null)
				{
					//Get the IEnumerable interface from the field.
					IEnumerable enumerable = (IEnumerable) fieldInfo.GetValue(obj);

					//This version support the IList and the 
					//IDictionary interfaces to iterate on collections.
					Type listType = fields[i].FieldType.GetInterface("IList", true);
					Type dictType = fields[i].FieldType.GetInterface("IDictionary", true);

					if (listType != null)
					{
						//Getting the IList interface.
						IList list = (IList) fields[i].GetValue(newObj);

						foreach (object o in enumerable)
						{
							//Checking to see if the current item 
							//support the ICloneable interface.
							isCloneable = o.GetType().GetInterface("ICloneable", true) != null ? true : false;

							if (isCloneable)
							{
								//If it does support the ICloneable interface, 
								//we use it to set the clone of
								//the object in the list.
								ICloneable clone = (ICloneable) o;
								list[j] = clone.Clone();
							}
							//else
							//{
							//throw new CloneNotSupportedException(o.GetType().FullName + " does not implement IClonable.");
							//}
							j++;
						}
					}
					else if (dictType != null)
					{
						//Getting the dictionary interface.
						IDictionary dic = (IDictionary) fields[i].GetValue(newObj);
						j = 0;

						foreach (DictionaryEntry entry in enumerable)
						{
							//Checking to see if the item 
							//support the ICloneable interface.
							isCloneable = entry.Value.GetType().GetInterface("ICloneable", true) != null ? true : false;

							if (isCloneable)
							{
								ICloneable clone = (ICloneable) entry.Value;
								dic[entry.Key] = clone.Clone();
							}
							//else
							//{
							//throw new CloneNotSupportedException(de.Value.GetType().FullName + " does not implement IClonable.");
							//}
							j++;
						}
					}
				}

				Array oldObjArray = null;
				Array objArray = null;

				if (fieldInfo.FieldType.IsArray || typeof (Array).IsAssignableFrom(fieldInfo.FieldType))
				{
					String arrType = fieldInfo.FieldType.FullName.Substring(0, fieldInfo.FieldType.FullName.IndexOf('['));
					oldObjArray = fieldInfo.GetValue(obj) as Array;
					objArray = Array.CreateInstance(Type.GetType(arrType), oldObjArray.Length);

					for (int k = 0; k < objArray.Length - 1; k++)
					{
						//We query if the fiels support the ICloneable interface.
						isCloneable = oldObjArray.GetValue(k).GetType().GetInterface("ICloneable", true) != null ? true : false;

						if (isCloneable)
						{
							//Getting the ICloneable interface from the object.
							ICloneable clone = (ICloneable) oldObjArray.GetValue(k);

							if (clone != null)
							{
								//We use the clone method to set the new value to the field.
								objArray.SetValue(clone.Clone(), k);
							}
						}
						else
						{
							// If the field doesn't support the ICloneable 
							// interface then just set it.
							objArray.SetValue(oldObjArray.GetValue(k), k);
							//throw new CloneNotSupportedException(fi.FieldType.FullName + " does not implement IClonable.");
						}
					}
					fields[i].SetValue(newObj, objArray);
				}

				i++;
			}
			return newObj;
		}
	}
}