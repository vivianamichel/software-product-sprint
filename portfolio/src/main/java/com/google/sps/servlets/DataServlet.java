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
import com.google.sps.data.DataStats;
import com.google.gson.Gson;
import java.util.Date;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

   private final Date dateCreated = new Date();
   public ArrayList<String> jsonData = new ArrayList<String>();


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    String json = new Gson().toJson(jsonData);
    response.getWriter().println(json);
  }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String[] userInputs = getUserInputs(request);
       String userName = userInputs[0];
       String userComment = userInputs[1];
       DataStats dataStats = new DataStats(dateCreated, userName, userComment);
       String json = convertToJson(dataStats);
       jsonData.add(json);
       response.setContentType("application/json;");
       response.getWriter().println(jsonData);
   }

    private String[] getUserInputs(HttpServletRequest request) {
        String userNameString = request.getParameter("name-choice");
        String userCommentString = request.getParameter("comment-choice");
        String[] inputs = { userNameString, userCommentString};
        return inputs;
    }

 private String convertToJson(DataStats dataStats) {
    String json = "{";
    json += "\"dataCreated\": ";
    json += "\"" + dataStats.getTime() + "\"";
    json += ", ";
    json += "\"userName\": ";
    json += "\"" + dataStats.getUserName()  + "\"";
    json += ", ";
    json += "\"userComment\": ";
    json += dataStats.getComment();
    json += "}";
    return json;
  }

   private String convertToJsonUsingGson(DataStats dataStats) {
    Gson gson = new Gson();
    String json = gson.toJson(dataStats);
    return json;
}
}
