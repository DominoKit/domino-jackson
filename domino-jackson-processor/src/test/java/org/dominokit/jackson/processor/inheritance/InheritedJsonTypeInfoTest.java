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
package org.dominokit.jackson.processor.inheritance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InheritedJsonTypeInfoTest {

  @Test
  public void subtypeOnSuperclassRoundTrips() {
    Circle circle = new Circle();
    circle.setLabel("a");
    circle.setRadius(3);
    Drawing drawing = new Drawing();
    drawing.setShape(circle);

    String json = Drawing_MapperImpl.INSTANCE.write(drawing);

    assertTrue(json.contains("\"kind\":\"circle\""));
    Drawing result = Drawing_MapperImpl.INSTANCE.read(json);
    assertEquals(Circle.class, result.getShape().getClass());
    assertEquals("a", result.getShape().getLabel());
    assertEquals(3, ((Circle) result.getShape()).getRadius());
  }
}
