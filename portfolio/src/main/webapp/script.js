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

function loadComments() {
  fetch("/list-comments").then(response => response.json()).then((comments) => {
    console.log("LOADING COMMENTS")
    const commentListElement = document.getElementById("comment-list");
    comments.forEach((comment) => {
      commentListElement.appendChild(createCommentElement(comment));
    })
  });
}

function translateComments() {
  fetch("/translate").then(response => response.json()).then((comments) => {
    console.log("LOADING TRANSLATED COMMENTS")
    const commentListElement = document.getElementById("comment-list");
    comments.forEach((comment) => {
      commentListElement.appendChild(createCommentElement(comment));
    })
  });
}


function createCommentElement(comment) {
  const commentElement = document.createElement('li');
  commentElement.className = 'comment';
 
  const titleElement = document.createElement('span');
  console.log("COMMENT " + comment.userComment);
  titleElement.innerText = comment.userComment;
 
  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteComment(comment);
    commentElement.remove();
  });
  commentElement.appendChild(titleElement);
  commentElement.appendChild(deleteButtonElement);
  return commentElement;
}
 
function deleteComment(comment) {
  const params = new URLSearchParams();
  params.append('id', comment.id);
  fetch('/delete-comment', {method: 'POST', body: params});
}

function requestTranslation() {
        const text = document.getElementById('text').value;
        const languageCode = document.getElementById('language').value;

        const resultContainer = document.getElementById('result');
        resultContainer.innerText = 'Loading...';

        const params = new URLSearchParams();
        params.append('text', text);
        params.append('languageCode', languageCode);

        fetch('/translate', {
          method: 'POST',
          body: params
        }).then(response => response.text())
        .then((translatedMessage) => {
          resultContainer.innerText = translatedMessage;
        });
      }
 
 



