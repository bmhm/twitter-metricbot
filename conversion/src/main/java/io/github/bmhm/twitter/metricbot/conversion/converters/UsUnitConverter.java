/*
 *  Copyright 2018 The twittermetricbot contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.github.bmhm.twitter.metricbot.conversion.converters;

import io.github.bmhm.twitter.metricbot.conversion.UnitConversion;
import java.util.Collection;
import java.util.List;

public interface UsUnitConverter {

  /**
   * List of search terms, the whole set in parantheses, seperated by OR and each enclosed in quotation marks.
   *
   * @return the list of search terms.
   */
  List<String> getSearchTerms();

  boolean matches(String text);

  Collection<UnitConversion> getConvertedUnits(String text);

}