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

package com.google.sps;

import java.util.Collection;
import java.util.ArrayList;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    ArrayList<TimeRange> possibleMeetingTimes = new ArrayList<TimeRange>();
    ArrayList<TimeRange> busyTimes = new ArrayList<TimeRange>();

    for(Event event: events){
        TimeRange eventTimeRange = event.getWhen();
        for(String attendee: event.getAttendees()){
            if (request.getAttendees().contains(attendee)){
                if (busyTimes.isEmpty()){
                    busyTimes.add((TimeRange.fromStartDuration(eventTimeRange.start(), eventTimeRange.duration())));
                } else if(busyTimes.get(busyTimes.size()-1).overlaps(eventTimeRange)){
                    if(busyTimes.get(busyTimes.size()-1).start() < eventTimeRange.start() && busyTimes.get(busyTimes.size()-1).end() > eventTimeRange.end()){
                        busyTimes.set(busyTimes.size()-1, (TimeRange.fromStartEnd(busyTimes.get(busyTimes.size()-1).start(), busyTimes.get(busyTimes.size()-1).end() - 1, true)));
                    } else if(busyTimes.get(busyTimes.size()-1).end() > eventTimeRange.start()){
                        busyTimes.set(busyTimes.size()-1, (TimeRange.fromStartEnd(busyTimes.get(busyTimes.size()-1).start(), eventTimeRange.end() - 1, true)));
                    }
                } else {
                    busyTimes.add((TimeRange.fromStartDuration(eventTimeRange.start(), eventTimeRange.duration())));
                }
            }
        }
    }
    if (busyTimes.isEmpty()){
        if (request.getDuration() > TimeRange.WHOLE_DAY.duration()){
            return possibleMeetingTimes;
        } 
        possibleMeetingTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, TimeRange.END_OF_DAY, true));
        return possibleMeetingTimes;
    }

    for(int i = 0; i < busyTimes.size(); i++){
        if (i==0) {
            if (busyTimes.get(i).start() - TimeRange.START_OF_DAY >= request.getDuration()){
                possibleMeetingTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, busyTimes.get(i).start() - 1, true));
            }
        } 
        if(i + 1 == busyTimes.size()){
           if (TimeRange.END_OF_DAY - busyTimes.get(i).end() >= request.getDuration()){
               possibleMeetingTimes.add(TimeRange.fromStartEnd(busyTimes.get(i).end(), TimeRange.END_OF_DAY, true));
           }
        } else if(busyTimes.get(i+1).start() - busyTimes.get(i).end() >= request.getDuration()){
                possibleMeetingTimes.add(TimeRange.fromStartEnd(busyTimes.get(i).end(), busyTimes.get(i+1).start() - 1,  true));
        }
    }
    return possibleMeetingTimes;
  }
}
