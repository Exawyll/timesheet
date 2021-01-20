import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IProject {
  id?: number;
  name?: string;
  description?: string;
  startDate?: Moment;
  endDate?: Moment;
  isActive?: boolean;
  user?: IUser;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public isActive?: boolean,
    public user?: IUser
  ) {
    this.isActive = this.isActive || false;
  }
}
