package com.agical.golddigger.model.fieldcreator.fields;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.agical.golddigger.model.Square;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.golddigger.model.fieldcreator.RandomFieldCreator;
import com.agical.golddigger.model.fieldcreator.ResourceFieldCreator;
import com.agical.jambda.Functions.Fn0;

public class CompetitionFields {
    
    public static Fn0<FieldCreator> factory = new Fn0<FieldCreator>(){
		@Override
		public FieldCreator apply() {
			return new ResourceFieldCreator(CompetitionFields.class);
		}
    };

	public static void main(String[] args) throws IOException {
        for(int i = 0; i < 100; i++) {
            int lat = (int) (Math.random()*20+5+i*0.2);
            int lon = (int) (Math.random()*20+5+i*0.2);
            int gold = (int) (Math.random()*lat*lon+20+i*0.5);
            int walls = (int) (Math.random()*lat*lon*0.2+5);
            RandomFieldCreator creator = new RandomFieldCreator(lat, lon, gold, walls);
            String field = Square.getField(creator.createField());
            File file = new File("target/fields/" + (i+11) + ".field");
            file.getParentFile().mkdirs();
            FileWriter  writer = new FileWriter(file);
            writer.write(field);
            writer.close();
        }
    }

	public static Fn0<FieldCreator>  factory(final int startField) {
		return new Fn0<FieldCreator>(){
			@Override
			public FieldCreator apply() {
				FieldCreator result = factory.apply();
				for (int i = 1; i < startField; i++) {
					result.createField();
				}
				return result;
			}
		};
	}
    
}
