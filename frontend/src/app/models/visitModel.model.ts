import {User} from './user.model';
import {Clinic} from './clinic.model';

export class VisitModel {

  id?: string | number;
  text?: string;
  start?: string;
  end?: string;
  endDate?: string;
  dayInterval?: number;
  minuteInterval?: number;
  careType?: string;
  price?: Number;
  user?: User;
  clinic?: Clinic;

}
