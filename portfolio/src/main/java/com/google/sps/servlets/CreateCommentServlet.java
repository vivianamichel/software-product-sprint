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
import com.google.sps.data.Comment;
import com.google.gson.Gson;
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/new-comment")
public class CreateCommentServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       long timestamp = System.currentTimeMillis();
       String userName = request.getParameter("name-choice");
       String userComment = request.getParameter("comment-choice");
       String languageCode = request.getParameter("languageCode");
       Translate translate = TranslateOptions.getDefaultInstance().getService();
       Translation translation =
            translate.translate(userComment, Translate.TranslateOption.targetLanguage(languageCode));
       String translatedText = translation.getTranslatedText();

       Entity commentEntity = new Entity("Comment");
       commentEntity.setProperty("timestamp", timestamp);
       commentEntity.setProperty("userName", userName);
       commentEntity.setProperty("userComment", translatedText);
       DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
       datastore.put(commentEntity);
       response.sendRedirect("/comments.html");
   }
}
