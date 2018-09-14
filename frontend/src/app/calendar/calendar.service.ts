import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {DayPilot} from 'daypilot-pro-angular';
import {HttpClient} from '@angular/common/http';
import {Clinic} from '../models/clinic.model';
import {VisitModel} from '../models/visitModel.model';

@Injectable()
export class CalendarService {

  private url = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  moveVisitModel(data: any): Observable<VisitModel> {
    return this.http.put(this.url + '/visitModels', data) as Observable<any>;
  }

  createVisitModel(data) {
    return this.http.post(this.url + '/visitModels', data);
  }

  getVisitModelsInWeek(from: DayPilot.Date, to: DayPilot.Date): Observable<any[]> {
    return this.http.get(this.url + '/visitModels?from=' + from.toString() + '&to=' + to.toString()) as Observable<any>;
  }

  getVisitsInWeek(from: DayPilot.Date, to: DayPilot.Date) {
    return this.http.get(this.url + '/visits?from=' + from.toString() + '&to=' + to.toString()) as Observable<any>;
  }

  getClinics() {
    return this.http.get<Clinic[]>(this.url + '/clinics');
  }

  deleteVisitModel(idVisitModel: String): Observable<VisitModel> {
    return this.http.delete(this.url + '/visitModels/' + idVisitModel) as Observable<any>;
  }

}


