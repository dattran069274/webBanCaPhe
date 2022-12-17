package meShop.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import meShop.model.CartItem;

@Component
public class MainConverter {
	public static String To_String(Serializable Demo_Object) throws IOException {
		ByteArrayOutputStream Byte_Array_Output_Stream = new ByteArrayOutputStream();
		ObjectOutputStream Object_Output_Stream = new ObjectOutputStream(Byte_Array_Output_Stream);
		Object_Output_Stream.writeObject(Demo_Object);
		Object_Output_Stream.close();
		return Base64.getEncoder().encodeToString(Byte_Array_Output_Stream.toByteArray());
	}
	public static String To_String(String Demo_Object) throws IOException {
		ByteArrayOutputStream Byte_Array_Output_Stream = new ByteArrayOutputStream();
		ObjectOutputStream Object_Output_Stream = new ObjectOutputStream(Byte_Array_Output_Stream);
		Object_Output_Stream.writeObject(Demo_Object);
		Object_Output_Stream.close();
		return Base64.getEncoder().encodeToString(Byte_Array_Output_Stream.toByteArray());
	}

	public static Object From_String(String s) throws IOException, ClassNotFoundException {
		byte[] Byte_Data = Base64.getDecoder().decode(s);
		ObjectInputStream Object_Input_Stream = new ObjectInputStream(new ByteArrayInputStream(Byte_Data));
		Object Demo_Object = Object_Input_Stream.readObject();
		Object_Input_Stream.close();
		return Demo_Object;
	}
}
