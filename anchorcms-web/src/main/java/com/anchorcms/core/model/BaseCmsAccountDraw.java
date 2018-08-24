package com.anchorcms.core.model;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_account_draw table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_account_draw"
 */

public abstract class BaseCmsAccountDraw implements Serializable {

	public static String REF = "CmsAccountDraw";
	public static String PROP_APPLY_TIME = "applyTime";
	public static String PROP_ACCOUNT_PAY = "accountPay";
	public static String PROP_APPLY_AMOUNT = "applyAmount";
	public static String PROP_APPLY_STATUS = "applyStatus";
	public static String PROP_ID = "id";
	public static String PROP_APPLY_ACCOUNT = "applyAccount";
	public static String PROP_DRAW_USER = "drawUser";


	// constructors
	public BaseCmsAccountDraw() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsAccountDraw(Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsAccountDraw(
		Integer id,
		com.anchorcms.core.model.CmsUser drawUser,
		String applyAccount,
		Double applyAmount,
		Short applyStatus,
		java.util.Date applyTime) {

		this.setId(id);
		this.setDrawUser(drawUser);
		this.setApplyAccount(applyAccount);
		this.setApplyAmount(applyAmount);
		this.setApplyStatus(applyStatus);
		this.setApplyTime(applyTime);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String applyAccount;
	private Double applyAmount;
	private Short applyStatus;
	private java.util.Date applyTime;

	// many to one
	private com.anchorcms.core.model.CmsUser drawUser;
	private com.anchorcms.core.model.CmsAccountPay accountPay;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="account_draw_id"
     */
	public Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: apply_account
	 */
	public String getApplyAccount () {
		return applyAccount;
	}

	/**
	 * Set the value related to the column: apply_account
	 * @param applyAccount the apply_account value
	 */
	public void setApplyAccount (String applyAccount) {
		this.applyAccount = applyAccount;
	}


	/**
	 * Return the value associated with the column: apply_amount
	 */
	public Double getApplyAmount () {
		return applyAmount;
	}

	/**
	 * Set the value related to the column: apply_amount
	 * @param applyAmount the apply_amount value
	 */
	public void setApplyAmount (Double applyAmount) {
		this.applyAmount = applyAmount;
	}


	/**
	 * Return the value associated with the column: apply_status
	 */
	public Short getApplyStatus () {
		return applyStatus;
	}

	/**
	 * Set the value related to the column: apply_status
	 * @param applyStatus the apply_status value
	 */
	public void setApplyStatus (Short applyStatus) {
		this.applyStatus = applyStatus;
	}


	/**
	 * Return the value associated with the column: apply_time
	 */
	public java.util.Date getApplyTime () {
		return applyTime;
	}

	/**
	 * Set the value related to the column: apply_time
	 * @param applyTime the apply_time value
	 */
	public void setApplyTime (java.util.Date applyTime) {
		this.applyTime = applyTime;
	}


	/**
	 * Return the value associated with the column: draw_user_id
	 */
	public com.anchorcms.core.model.CmsUser getDrawUser () {
		return drawUser;
	}

	/**
	 * Set the value related to the column: draw_user_id
	 * @param drawUser the draw_user_id value
	 */
	public void setDrawUser (com.anchorcms.core.model.CmsUser drawUser) {
		this.drawUser = drawUser;
	}


	/**
	 * Return the value associated with the column: account_pay_id
	 */
	public com.anchorcms.core.model.CmsAccountPay getAccountPay () {
		return accountPay;
	}

	/**
	 * Set the value related to the column: account_pay_id
	 * @param accountPay the account_pay_id value
	 */
	public void setAccountPay (com.anchorcms.core.model.CmsAccountPay accountPay) {
		this.accountPay = accountPay;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.anchorcms.core.model.CmsAccountDraw)) return false;
		else {
			com.anchorcms.core.model.CmsAccountDraw cmsAccountDraw = (com.anchorcms.core.model.CmsAccountDraw) obj;
			if (null == this.getId() || null == cmsAccountDraw.getAccountDrawId()) return false;
			else return (this.getId().equals(cmsAccountDraw.getAccountDrawId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}