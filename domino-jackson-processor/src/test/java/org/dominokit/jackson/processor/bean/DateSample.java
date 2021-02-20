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
package org.dominokit.jackson.processor.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.dominokit.jackson.annotation.JSONMapper;
import org.dominokit.jackson.utils.DatePatterns;

@JSONMapper
public class DateSample {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePatterns.SHORT)
  private Date dateOnly;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePatterns.ISO8601)
  private Date dateAndTime;

  public Date getDateOnly() {
    return dateOnly;
  }

  public void setDateOnly(Date dateOnly) {
    this.dateOnly = dateOnly;
  }

  public Date getDateAndTime() {
    return dateAndTime;
  }

  public void setDateAndTime(Date dateAndTime) {
    this.dateAndTime = dateAndTime;
  }
}
