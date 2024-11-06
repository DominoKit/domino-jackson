/*
 * Copyright © 2019 Dominokit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dominokit.jackson.records;

import org.junit.Assert;
import org.junit.Test;

public class RecordIT {

  private static final Point_MapperImpl POINT_MAPPER = new Point_MapperImpl();
  private static final Circle_MapperImpl CIRCLE_MAPPER = new Circle_MapperImpl();
  private static final ComplexNumber_MapperImpl COMPLEX_NUMBER_MAPPER = new ComplexNumber_MapperImpl();
  private static final Rectangle_MapperImpl RECTANGLE_MAPPER = new Rectangle_MapperImpl();

  @Test
  public void simpleRecordTest(){
    String pointJson = POINT_MAPPER.write(new Point(10, 20));
    Point point = POINT_MAPPER.read(pointJson);

    Assert.assertEquals("{\"x\":10,\"y\":20}", pointJson);
    Assert.assertEquals(10, point.x());
    Assert.assertEquals(20, point.y());
    Assert.assertEquals(new Point(10, 20), point);

    String circleJson = CIRCLE_MAPPER.write(new Circle(10.0));
    Circle circle = CIRCLE_MAPPER.read(circleJson);

    Assert.assertEquals("{\"radius\":10.0}", circleJson);
    Assert.assertEquals(10.0, circle.radius(), 0.001);
    Assert.assertEquals(new Circle(10), circle);

    String complexNumberJson = COMPLEX_NUMBER_MAPPER.write(new ComplexNumber(10.0));
    ComplexNumber complexNumber = COMPLEX_NUMBER_MAPPER.read(complexNumberJson);

    Assert.assertEquals("{\"real\":10.0,\"imaginary\":0.0}", complexNumberJson);
    Assert.assertEquals(10.0, complexNumber.real(), 0.001);
    Assert.assertEquals(0, complexNumber.imaginary(), 0.001);
    Assert.assertEquals(new ComplexNumber(10.0), complexNumber);

    String rectangleJson = RECTANGLE_MAPPER.write(new Rectangle(new Point(10,20), new Point(20, 30)));
    Rectangle rectangle = RECTANGLE_MAPPER.read(rectangleJson);

    Assert.assertEquals("{\"bottomLeft\":{\"x\":10,\"y\":20},\"topRight\":{\"x\":20,\"y\":30}}", rectangleJson);
    Assert.assertEquals(new Point(10, 20), rectangle.a());
    Assert.assertEquals(new Point(20, 30), rectangle.b());
    Assert.assertEquals(new Rectangle(new Point(10,20), new Point(20, 30)), rectangle);

  }

}
