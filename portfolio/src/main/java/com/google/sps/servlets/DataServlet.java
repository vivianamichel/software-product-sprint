// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
// import com.google.sps.data.ServerStats;
import com.google.gson.Gson;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
   private ArrayList<String> toDisplay;

  @Override
  public void init() {
    toDisplay = new ArrayList<>();
    toDisplay.add("Hi, my first name is Viviana");
    toDisplay.add("Last name Michel");
    toDisplay.add("Today is the 14th");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    response.getWriter().println("Hello Viviana!");
    String json = convertToJson(toDisplay);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertToJson(ArrayList toDisplay) {
    String json = "{";
    json += " " + toDisplay.get(0);
    json += " " + toDisplay.get(1);
    json += " " + toDisplay.get(2);
    json += "}";
    return json;
  }

   private String convertToJsonUsingGson(ArrayList toDisplay) {
    Gson gson = new Gson();
    String json = gson.toJson(toDisplay);
    return json;
}
}
