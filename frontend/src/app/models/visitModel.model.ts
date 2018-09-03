import {User} from './user.model';
import {Clinic} from './clinic.model';

export class VisitModel {

  idVisitModel?: number;
  text?: string;
  start?: string;
  end?: string;
  endDate?: string;
  dayInterval?: number;
  minuteInterval?: number;
  specialization?: string;
  careType?: string;
  user?: User;
  clinic?: Clinic;

}
