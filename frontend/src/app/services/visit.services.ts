import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {DayPilot} from 'daypilot-pro-angular';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class VisitService {

  private url = 'http://localhost:8080/visits/';

  constructor(private http: HttpClient) {
  }

  getVisitsInWeek(from: DayPilot.Date, to: DayPilot.Date) {
    return this.http.get(this.url + '?from=' + from.toString() + '&to=' + to.toString()) as Observable<any>;
  }

  bookVisit(idVisit: String, idUser: String | Number) {
    return this.http.get(this.url + 'book/' + idVisit + '?idUser=' + idUser);
  }

}


